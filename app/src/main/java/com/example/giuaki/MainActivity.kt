package com.example.giuaki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
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
import androidx.compose.material3.Divider
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.colorResource


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
            bottomBar = { BottomIconsBar() }
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
                    val productDescription = "Bấm mua liền tay để nhận được những ưu đãi lớn nhất"
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
        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "", tint = Color.DarkGray)
        }
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "", tint = Color.DarkGray)
        }
    }
}

@Composable
fun Content(navController: NavController, paddingValues: PaddingValues) {
    Column {
        Spacer(modifier = Modifier.height(100.dp))
        Header()
        Spacer(modifier = Modifier.height(16.dp))
        Promotions()
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection()
        Spacer(modifier = Modifier.height(16.dp))
        BestSellerSection(navController)
        Spacer(modifier = Modifier.height(16.dp))
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
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.hot_dog),
                title = "Hotdog",
                backgroundColor = Color(0xff6EB6F5)
            )
        }
        item {
            PromotionItem(
                imagePainter = painterResource(id = R.drawable.sashimi_platter),
                title = "sashimi",
                backgroundColor = colorResource(R.color.darkBrown)
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
                text = "Danh mục",
                style = MaterialTheme.typography.headlineSmall // Thay thế h6 bằng headlineSmall
            )
            TextButton(onClick = {}) {
                Text(text = "Thêm", color = MaterialTheme.colorScheme.primary)
            }
        }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
            CategoryButton(
                text = "Thịt",
                icon = painterResource(id = R.drawable.thit),
                backgroundColor = Color(0xffF6E6E9)
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
            Text(text = "Sản phẩm", style = MaterialTheme.typography.headlineSmall)
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
                navController = navController
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.mint_lemonade),
                title = "Nước chanh",
                price = 20000,
                navController = navController
            )
        }
        item {
            BestSellerItem(
                imagePainter = painterResource(id = R.drawable.veggie_extravaganza),
                title = "Bánh bò",
                price = 150000,
                navController = navController
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
    Review("Toan", 4, "Rất tuyệt vời rất sạch."),
    Review("Khang", 2, "ăn rất đậm vị."),
    Review("Tri", 10, "giá rất tốt mọi người nên thử."),
    Review("Nhật", 3, "sẽ ghé ủng hộ thêm."),
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
                Text(text = "4.5", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "(300)", fontSize = 16.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "500 Lượt bán", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Miễn phí vận chuyển", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút "Add to Cart"
        Button(
            onClick = { /* No action for now */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Thêm vào giỏ hàng")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hiển thị mô tả chi tiết sản phẩm
        Text(text = "Mô tả sản phẩm", fontWeight = FontWeight                                                                                        .Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productDescription, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Hiển thị danh sách đánh giá
        Text(text = "Đánh giá khách hàng", fontWeight = FontWeight.Bold, fontSize = 20.sp)
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
fun BottomIconsBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.darkPurple)) // Màu cam cho thanh nền
            .padding(8.dp) // Khoảng cách từ icon đến cạnh thanh màu cam
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly, // Căn đều các icon
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Outlined.Home, contentDescription = "", tint = Color.White)
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Outlined.ShoppingCart, contentDescription = "", tint = Color.White)
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "", tint = Color.White)
            }
        }
    }
}









