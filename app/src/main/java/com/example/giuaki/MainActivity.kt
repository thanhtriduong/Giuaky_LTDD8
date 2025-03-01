package com.example.giuaki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.ui.text.font.FontStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainTheme {
                HomeScreen()
            }
        }

    }
}
@Composable
fun mainTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    mainTheme {
        HomeScreen()
    }
}
@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    // Bố cục chính của giao diện không thay đổi
    Box {
        // Background hình ảnh
        Image(
            painter = painterResource(id = R.drawable.arc_bg),
            contentDescription = "Header Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .offset(0.dp, (-30).dp)
                .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp)),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = { AppBar() },
            containerColor = Color.Transparent,
            bottomBar = { MyBottomBar() }
        ) { paddingValues ->
            // Điều hướng giữa màn hình chính và chi tiết sản phẩm
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    Content(
                        navController = navController,
                        paddingValues = paddingValues
                        )
                }
                composable("detail/{title}/{price}/{discountPercent}") { backStackEntry ->
                    val title = backStackEntry.arguments?.getString("title") ?: ""
                    val price = backStackEntry.arguments?.getString("price") ?.toInt() ?: 0
                    val discountPercent = backStackEntry.arguments?.getString("discountPercent")?.toInt() ?: 0
                    val imagePainter = painterResource(id = R.drawable.grilled_ribeye_steak)
                    val productDescription = "Thịt bò được nhập khẩu từ Mỹ, với vị tươi ngon thượng hạng của trời Âu."
                    ProductDetailScreen(navController, title, price, discountPercent, imagePainter,productDescription)
                }
            }
        }
    }
}
@Composable
fun HomeContent(navController: NavController) {
    Scaffold(
        topBar = { AppBar() }, // Thanh công cụ
        content = { paddingValues ->
            Content(navController = navController, paddingValues = paddingValues)
        }
    )
}

@Composable
fun AppBar() {
    Row(
        Modifier
            .padding(16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Tìm kiếm", fontSize = 12.sp) },
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
    Row(
        Modifier
            .padding(top = 84.dp)
            .padding(start = 16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Chào mừng bạn đến với TT Restaurant",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            color = colorResource(R.color.orange)
        )
    }
}

@Composable
fun Content(navController: NavController, paddingValues: PaddingValues) {
    Column {
        Spacer(modifier = Modifier.height(100.dp))
        Header()
        Promotions()
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection()
        Spacer(modifier = Modifier.height(16.dp))
        BestSellerSection(navController)
    }
}
@Composable
fun Header() {
    Card(
        modifier = Modifier
            .height(64.dp)
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),  // Sửa dụng CardDefaults cho elevation
        shape = RoundedCornerShape(8.dp)
    ) {
            }
}

@Composable
fun Promotions() {
    LazyRow(
        Modifier.height(160.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.pizza),
                title = "Pizza",
                backgroundColor = colorResource(R.color.orange)
            )
        }
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.hot_dog),
                title = "Hotdog",
                backgroundColor = colorResource(R.color.darkBrown)
            )
        }
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.sashimi_platter),
                title = "sashimi",
                backgroundColor = colorResource(R.color.darkPurple)
            )
        }
    }
}

@Composable
fun PromotionItem(
    title: String = "",
    backgroundColor: Color = Color.Transparent,
    imagePainter: Painter
) {
    Card(
        modifier = Modifier.width(300.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, fontSize = 14.sp, color = Color.White)
            }
            Image(
                painter = imagePainter, contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                alignment = Alignment.CenterEnd,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CategorySection() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Text(
                text = "Danh mục sản phẩm",
                style = MaterialTheme.typography.headlineSmall // Thay thế h6 bằng headlineSmall
            )
            TextButton(onClick = {}) {
                Text(text = "Thêm", color = MaterialTheme.colorScheme.primary)
            }
        }
        }

        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryButton(
                text = "Thịt",
                icon = painterResource(id = R.drawable.thit),
                backgroundColor = Color(0xffF6E6E9)
            )
            CategoryButton(
                text = "Hamburger",
                icon = painterResource(id = R.drawable.hambur),
                backgroundColor = Color(0xffFEF4E7)
            )
            CategoryButton(
                text = "Rau củ",
                icon = painterResource(id = R.drawable.raucu),
                backgroundColor = Color(0xffF6FBF3)
            )
            CategoryButton(
                text = "Sữa",
                icon = painterResource(id = R.drawable.milk),
                backgroundColor = Color(0xffFFFBF3)
            )
        }
    }

@Composable
fun CategoryButton(
    text: String = "",
    icon: Painter,
    backgroundColor: Color
) {
    Column(
        Modifier
            .width(72.dp)
            .clickable { }
    ) {
        Box(
            Modifier
                .size(72.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(18.dp)
        ) {
            Image(painter = icon, contentDescription = "", modifier = Modifier.fillMaxSize())
        }
        Text(text = text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 12.sp)
    }
}
@Composable
fun BestSellerSection(navController: NavController) {
    Column() {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Yêu thích", style = MaterialTheme.typography.headlineSmall)
            TextButton(onClick = {}) {
                Text(text = "Thêm", color = MaterialTheme.colorScheme.primary)
            }
        }

        BestSellerItems(navController)
    }
}

@Composable
fun BestSellerItems(navController: NavController) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.grilled_ribeye_steak),
                title = "Bò bít tết",
                price = 50000,
                navController = navController,
                color = colorResource(R.color.darkPurple)
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.mint_lemonade),
                title = "Nước chanh",
                price = 20000,
                navController = navController,
                color = colorResource(R.color.darkPurple)
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.veggie_extravaganza),
                title = "Bánh bò",
                price = 150000,
                navController = navController,
                color = colorResource(R.color.darkPurple)
            )
        }
    }
}

@Composable
fun BestSellerItem(
    title: String = "",
    price: Int = 0,
    discountPercent: Int = 0,
    rating: Float = 0f,
    imagePainter: Painter,
    color: Color,
    navController: NavController
) {
    // Calculate discounted price
    val discountedPrice = price * (100 - discountPercent) / 100

    Card(
        Modifier
            .width(160.dp)
            .clickable {
                navController.navigate("detail/$title/$price/$discountPercent")
            }
    ) {
        Column(
            Modifier
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = imagePainter, contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Row {
                    Text(
                        "$price đ",
                        textDecoration = if (discountPercent > 0)
                            TextDecoration.LineThrough
                        else
                            TextDecoration.None,
                        color = if (discountPercent > 0) Color.Gray else Color.Black
                    )
                    if (discountPercent > 0) {
                        Text(
                            text = " [$discountPercent%]",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                if (discountPercent > 0) {
                    Text(
                        text = "$discountedPrice đ",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

data class Review(
    val userName: String,
    val rating: Int,
    val comment: String
)

val sampleReviews = listOf(
    Review("Nguyen Van A", 5, "Tươi ngon tuyệt vời! Sẽ ủng hộ thêm.."),
    Review("Thanh Tri", 2, "Thịt không đậm đà!"),
    Review("Tran Thi B", 4, "Ngon bỏ rẻ mọi người nên thử."),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    title: String,
    price: Int,
    discountPercent: Int,
    imagePainter: Painter,
    productDescription: String
) {
    // Calculate discounted price
    val discountedPrice = price * (100 - discountPercent) / 100

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Nút "Back"
        TopAppBar(
            title = { Text(text = "Product Detail") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
        // Hình ảnh sản phẩm
        Image(
            painter = imagePainter,
            contentDescription = "",
            modifier = Modifier
                .size(180.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Tiêu đề sản phẩm
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // Giá sản phẩm
        Text(
            "$price đ",
            textDecoration = if (discountPercent > 0)
                TextDecoration.LineThrough
            else
                TextDecoration.None,
            color = if (discountPercent > 0) Color.Gray else Color.Black,
            fontSize = 20.sp
        )

        // Giảm giá
        if (discountPercent > 0) {
            Text(
                text = "Giảm $discountPercent%",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
            // Hiển thị giá sau khi giảm
            Text(
                text = "$discountedPrice đ",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Hiển thị khối lượng, đánh giá, số lượng bán, và giao hàng miễn phí
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Đánh giá", tint = Color(0xFFFFD700)) // Icon sao vàng
                Text(text = "4.8", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "(300)", fontSize = 16.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "1,2k lượt bán", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Voucher 15%", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút "Add to Cart"
        Button(
            onClick = { /* No action for now */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.darkPurple)
            )
        ) {
            Text(text = "Thêm vào giỏ hàng")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hiển thị mô tả chi tiết sản phẩm
        Text(text = "Mô tả", fontWeight = FontWeight                                                                                        .Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productDescription, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Hiển thị danh sách đánh giá
        Text(text = "Đánh giá sản phẩm", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(sampleReviews) { review ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = review.userName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Row {
                            repeat(review.rating) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Star",
                                    tint = Color(0xFFFFD700)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = review.comment, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
@Preview
fun MyBottomBar() {
    val bottomMenuItemsList= prepareBottomMenu()
    val contect = LocalContext.current
    var selectedItem by remember{ mutableStateOf("Home") }

    BottomAppBar(
        backgroundColor = colorResource(R.color.grey),
        elevation = 3.dp
    ) {
        bottomMenuItemsList.forEach{bottomMenuItem ->
            BottomNavigationItem(
                selected = (selectedItem==bottomMenuItem.label),
                onClick = {
                    selectedItem=bottomMenuItem.label
                    if(bottomMenuItem.label =="Cart") {

                    } else {

                    }
                },
                icon = {
                    Icon(
                        painter=bottomMenuItem.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(20.dp)
                    )
                }
            )
        }
    }
}

data class BottomMenuItem(
    val label:String, val icon: Painter
)

@Composable
fun prepareBottomMenu():List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(BottomMenuItem(label = "Home", icon = painterResource(R.drawable.btn_1)))
    bottomMenuItemList.add(BottomMenuItem(label = "Cart", icon = painterResource(R.drawable.btn_2)))
    bottomMenuItemList.add(BottomMenuItem(label = "Favourite", icon = painterResource(R.drawable.btn_3)))
    bottomMenuItemList.add(BottomMenuItem(label = "Order", icon = painterResource(R.drawable.btn_4)))
    bottomMenuItemList.add(BottomMenuItem(label = "Profile", icon = painterResource(R.drawable.btn_5)))

    return bottomMenuItemList
}








