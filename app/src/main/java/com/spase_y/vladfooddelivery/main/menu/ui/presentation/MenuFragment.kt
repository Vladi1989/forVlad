package com.spase_y.vladfooddelivery.main.menu.ui.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentMenuBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.promotions_adapters.PromotionsAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.recommend_menu_adapter.RecommendMenuAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters.MenuAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.model.MenuScreenState
import com.spase_y.vladfooddelivery.main.menu.ui.view_model.MenuViewModel
import com.spase_y.vladfooddelivery.main.order.order_main.ui.presentation.CurrentOrderFragment
import org.koin.android.ext.android.inject
import kotlin.collections.ArrayList



class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val handler = Handler(Looper.getMainLooper())
    val vm by inject<MenuViewModel>() //1 OrderViewModel


    private val buttonTextList = listOf("Order Now", "Get Discount", "Shop Now")
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: PromotionsAdapter
    private lateinit var imageList: ArrayList<Int>
    private lateinit var handlerImageSlider: Handler
    private lateinit var recommendMenuAdapter: RecommendMenuAdapter
    private lateinit var menuAdapter: MenuAdapter


    private val recommendItems = listOf(
        MenuItem(
            R.drawable.icon_item_menu,
            "Margherita Pizza",
            "Crispy crust, tomato sauce, fresh mozzarella, and basil.",
            1.29f
        ),
        MenuItem(
            R.drawable.icon_item_menu,
            "Margherita Pizza",
            "Tomato sauce, fresh mozzarella, and basil.",
            3.29f
        ),
    )
    private val menuItems = listOf(
        MenuItem(
            R.drawable.icon_item_menu,
            "Pepperoni Pizza",
            "Tomato sauce, mozzarella, pepperoni.",
            14.99f
        ),
        MenuItem(
            R.drawable.icon_item_menu,
            "Veggie Pizza",
            "Tomato sauce, mozzarella, peppers, onions, olives.",
            12.49f
        ),
        MenuItem(
            R.drawable.icon_item_menu,
            "Pepperoni Pizza",
            "Tomato sauce, mozzarella, pepperoni.",
            14.99f
        ),
        MenuItem(
            R.drawable.icon_item_menu,
            "Veggie Pizza",
            "Tomato sauce, mozzarella, peppers, onions, olives.",
            12.49f
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        viewPager2 = binding.imageSlider
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getMenuLd().observe(viewLifecycleOwner){
            when(it){
                is MenuScreenState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is MenuScreenState.Error -> {
                    Toast.makeText(requireContext(), "Корзина переполнена", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                    vm.clearMenuLD()

                }
                is MenuScreenState.Succes -> {
                    Toast.makeText(requireContext(), "Успешно добавлено", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                    vm.clearMenuLD()

                }
            }
        }

        binding.cvCart.setOnClickListener{
            replaceFragment(CurrentOrderFragment())
        }

        init()
        setTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager2.removeCallbacks(runnable)
                viewPager2.postDelayed(runnable,2000)
            }

        })
        setUpMenuRecyclerViews()
    }



    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMainApp,fragment)
            .addToBackStack(null)
            .commit()
    }


    private val runnable = Runnable{
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(10))
        transformer.addTransformer{page,position->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
    }

    override fun onPause() {
        super.onPause()
        viewPager2.removeCallbacks(runnable)
    }

    override fun onStart() {
        super.onStart()
        viewPager2.postDelayed(runnable, 2000)
    }

    private fun init() {
        imageList = ArrayList()
        adapter = PromotionsAdapter(requireContext(),imageList,viewPager2)
        handlerImageSlider = Handler(Looper.myLooper()!!)

        imageList.add(R.drawable.image_view_pager)
        imageList.add(R.drawable.image_view_pager)
        imageList.add(R.drawable.image_view_pager)
        imageList.add(R.drawable.image_view_pager)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false //если сделаю тру, то будет показываться один image, а не 3
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpMenuRecyclerViews() {
        recommendMenuAdapter = RecommendMenuAdapter(recommendItems)
        binding.rvRecommend.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommend.adapter = recommendMenuAdapter
        val itemClickListener = object : MenuAdapter.OnItemClickListener{
            override fun onItemClick(item: MenuItem) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fcvMainApp, DetailsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        menuAdapter = MenuAdapter(menuItems,itemClickListener, { item ->
            vm.addMenuItemToOrder(item)
        })

        binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMenu.adapter = menuAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        _binding = null
    }
}
