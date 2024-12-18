package com.spase_y.vladfooddelivery.main.menu.ui.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.core.toPx
import com.spase_y.vladfooddelivery.databinding.FragmentMenuBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.PromotionItem
import com.spase_y.vladfooddelivery.main.menu.data.model.TabData
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.promotions_adapters.PromotionsAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters.MenuAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.recommend_menu_adapter.HorizontalSpaceItemDecoration
import com.spase_y.vladfooddelivery.main.menu.ui.model.MenuScreenState
import com.spase_y.vladfooddelivery.main.menu.ui.view_model.MenuViewModel
import com.spase_y.vladfooddelivery.main.order.order_main.ui.model.OrderScreenState
import com.spase_y.vladfooddelivery.main.order.order_main.ui.presentation.CurrentOrderFragment
import com.spase_y.vladfooddelivery.main.root.MainAppFragment
import org.koin.android.ext.android.inject


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val handler = Handler(Looper.getMainLooper())
    val vm by inject<MenuViewModel>()
    private lateinit var adapter: PromotionsAdapter
    private lateinit var handlerImageSlider: Handler
    private lateinit var menuAdapter: MenuAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMenuRecyclerViews()

        MainAppFragment.getInstance().showNavigation()

        vm.menuLd.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MenuScreenState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is MenuScreenState.Error -> {
                    Toast.makeText(requireContext(), "Ошибка: ${state.errorMessage}", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
                is MenuScreenState.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    setupTabLayout(binding.tlTabLayout,state.menu)
                    menuAdapter.updateItems(state.menu.pizzaItems)
                    initPromotions(state.menu.promotionItems)
                }
            }
        }
        vm.orderLd.observe(viewLifecycleOwner) { orderState ->
            when (orderState) {
                is OrderScreenState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is OrderScreenState.Error -> {
                    Toast.makeText(requireContext(), "Ошибка заказа: ${orderState.errorMessage}", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
                is OrderScreenState.Success -> {
                    Toast.makeText(requireContext(), "Продукт успешно обновлен", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }

        binding.edSearch.onFocusChangeListener = View.OnFocusChangeListener{_,hasFocus ->
            if(hasFocus){
                binding.llSearchFood.setBackgroundResource(R.drawable.button_shape_stroke)
            } else {
                binding.llSearchFood.setBackgroundResource(R.drawable.shape_search_view_stroke_text5)
            }
        }
        binding.cvCart.setOnClickListener{
            replaceFragment(CurrentOrderFragment())
        }

        setTransformer()
        binding.imageSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.imageSlider.removeCallbacks(runnable)
                binding.imageSlider.postDelayed(runnable,2000)
            }

        })
        binding.tlTabLayout.selectTab(binding.tlTabLayout.getTabAt(0))
        setupRecyclerView()
    }
    private fun setupTabLayout(tabLayout: TabLayout, menu: ListMenu) {
        val tabs = listOf(
            TabData("Pizza", R.drawable.icon_menu_4),
            TabData("Burgers", R.drawable.icon_menu_1),
            TabData("Wok", R.drawable.icon_menu_2),
            TabData("Sushi", R.drawable.icon_menu_3)
        )

        tabs.forEach { tabData ->
            val tab = tabLayout.newTab()
            tab.customView = createTabView(tabData)
            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> updateRecyclerView(menu.pizzaItems)
                    1 -> updateRecyclerView(menu.hamburgersItems)
                    2 -> updateRecyclerView(menu.wokItems)
                    3 -> updateRecyclerView(menu.sushiItems)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun createTabView(tabData: TabData): View {
        val tabView = LayoutInflater.from(requireContext())
            .inflate(R.layout.tab_item, null)
        val tabIcon = tabView.findViewById<ImageView>(R.id.tab_icon)
        val tabText = tabView.findViewById<TextView>(R.id.tab_text)
        tabIcon.setImageResource(tabData.iconRes)
        tabText.text = tabData.title
        return tabView
    }
    private fun setupRecyclerView() {
        menuAdapter = MenuAdapter(
            menuItems = emptyList(),
            listener = object : MenuAdapter.OnItemClickListener {
                override fun onItemClick(item: Item) {
                    val detailsFragment = DetailsFragment()
                    val bundle = Bundle()
                    val jsonItem = Gson().toJson(item)
                    bundle.putString("item", jsonItem)
                    detailsFragment.arguments = bundle
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fcvMainApp, detailsFragment)
                        .addToBackStack(null)
                        .commit()
                }
            },
            onAddClick = { item ->
                vm.addMenuItemToOrder(item)
            }
        )
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), 2) // 2 - количество элементов в строке
            adapter = menuAdapter
        }
    }
    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMainApp,fragment)
            .addToBackStack(null)
            .commit()
    }
    private val runnable = Runnable{
        binding.imageSlider.currentItem = binding.imageSlider.currentItem + 1
    }
    private fun setTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(10))
        transformer.addTransformer{page,position->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.imageSlider.setPageTransformer(transformer)
    }
    override fun onPause() {
        super.onPause()
        binding.imageSlider.removeCallbacks(runnable)
    }
    override fun onStart() {
        super.onStart()
        binding.imageSlider.postDelayed(runnable, 2000)
    }
    private fun initPromotions(list: List<PromotionItem>) {

        adapter = PromotionsAdapter()
        handlerImageSlider = Handler(Looper.myLooper()!!)
        adapter.promotionList = list

        binding.imageSlider.adapter = adapter
        binding.imageSlider.offscreenPageLimit = list.size
        binding.imageSlider.clipToPadding = false //если сделаю тру, то будет показываться один image, а не 3
        binding.imageSlider.clipChildren = false
        binding.imageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
    private fun updateRecyclerView(items: List<Item>) {
        menuAdapter.updateItems(items)
    }
    private fun setUpMenuRecyclerViews() {


        binding.rvRecommend.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommend.addItemDecoration(HorizontalSpaceItemDecoration(28.toPx(requireContext())))

        val itemClickListener = object : MenuAdapter.OnItemClickListener{
            override fun onItemClick(item: Item) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fcvMainApp, DetailsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
        _binding = null
    }
}
