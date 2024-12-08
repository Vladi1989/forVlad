package com.spase_y.vladfooddelivery.main.menu.ui.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlin.concurrent.thread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.core.toPx
import com.spase_y.vladfooddelivery.databinding.FragmentMenuBinding
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.promotions_adapters.PromotionsAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.recommend_menu_adapter.RecommendMenuAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.menu_adapters.MenuAdapter
import com.spase_y.vladfooddelivery.main.menu.ui.adapters.recommend_menu_adapter.HorizontalSpaceItemDecoration
import com.spase_y.vladfooddelivery.main.menu.ui.model.MenuScreenState
import com.spase_y.vladfooddelivery.main.menu.ui.view_model.MenuViewModel
import com.spase_y.vladfooddelivery.main.order.order_main.ui.presentation.CurrentOrderFragment
import com.spase_y.vladfooddelivery.main.root.MainAppFragment
import com.spase_y.vladfooddelivery.root.Constants.GET_URL_FROM_BACK
import okhttp3.Request
import okhttp3.OkHttpClient
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
        Item(
            item_calories = 200,
            item_description = "Crispy crust, tomato sauce, fresh mozzarella, and basil.",
            item_id = "1",
            item_image = R.drawable.icon_item_menu, // Используем ресурс изображения
            item_is_vegan = false,
            item_name = "Margherita Pizza",
            item_price = 1.29,
            quantity = 1
        ),
        Item(
            item_calories = 250,
            item_description = "Tomato sauce, fresh mozzarella, and basil.",
            item_id = "2",
            item_image = R.drawable.icon_item_menu, // Используем ресурс изображения
            item_is_vegan = true,
            item_name = "Vegan Margherita Pizza",
            item_price = 3.29,
            quantity = 1
        )
    )


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

        viewPager2 = binding.imageSlider

        MainAppFragment.getInstance().showNavigation()

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
                is MenuScreenState.Success -> {
                    Toast.makeText(requireContext(), "Успешно добавлено", Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                    vm.clearMenuLD()

                }
                null -> {
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

        init()
        setTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPager2.removeCallbacks(runnable)
                viewPager2.postDelayed(runnable,2000)
            }

        })
        // Настройка RecyclerView
        setupRecyclerView()

        // Выполнение сетевого запроса
        fetchMenuItems()
    }

    private fun setupRecyclerView() {
        menuAdapter = MenuAdapter(
            menuItems = emptyList(),
            listener = object : MenuAdapter.OnItemClickListener {
                override fun onItemClick(item: Item) {
                    Toast.makeText(requireContext(), "Clicked: ${item.item_name}", Toast.LENGTH_SHORT).show()
                }
            },
            onAddClick = { item ->
                Toast.makeText(requireContext(), "Added: ${item.item_name}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), 2) // 2 - количество элементов в строке
            adapter = menuAdapter
        }
    }

    private fun fetchMenuItems() {
        val url = GET_URL_FROM_BACK
        val client = OkHttpClient()

        requireActivity().runOnUiThread {
            binding.pbLoading.visibility = View.VISIBLE
        }

        thread {
            try {
                val request = Request.Builder()
                    .url(url)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw Exception("Ошибка запроса: ${response.code}")

                    val responseBody = response.body?.string() ?: throw Exception("Пустой ответ от сервера")
                    Log.d("MenuFragment", "Ответ от сервера: $responseBody")

                    val listMenu = Gson().fromJson(responseBody, ListMenu::class.java)
                    val items = listMenu.items

                    Log.d("MenuFragment", "Количество элементов: ${items.size}")

                    // Обновляем адаптер новыми элементами в главном потоке
                    requireActivity().runOnUiThread {
                        menuAdapter.updateItems(items)
                        binding.pbLoading.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
                Log.e("MenuFragment", "Ошибка загрузки меню: ${e.message}")
                requireActivity().runOnUiThread {
                    binding.pbLoading.visibility = View.GONE
                    // Вывод ошибки пользователю (например, через Toast)
                    Toast.makeText(requireContext(), "Ошибка загрузки данных: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
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
        val recommendItemClickListener = object : RecommendMenuAdapter.OnItemClickListener{
            override fun onItemClick(item: Item) {
                Toast.makeText(requireContext(),"${item.item_name} clicked", Toast.LENGTH_SHORT).show()
            }
        }
        recommendMenuAdapter = RecommendMenuAdapter(
            items = recommendItems,
            listener = recommendItemClickListener,
            onAddClick = { item:Item ->
                vm.addMenuItemToOrder(item)
                Toast.makeText(requireContext(), "${item.item_name} added to cart", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvRecommend.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommend.adapter = recommendMenuAdapter

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
