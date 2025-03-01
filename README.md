# Hình ảnh minh họa
![](https://github.com/thanhtriduong/Giuaky_LTDD8/blob/master/app/src/main/res/drawable/anhminhhoa1.png)
![](https://github.com/thanhtriduong/Giuaky_LTDD8/blob/master/app/src/main/res/drawable/anhminhhoa2.png)

# Các bước thực hiện
- Vào mục build.gradle.kts (:app) và thêm vào **dependencies**:
```implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.compose.runtime:runtime-livedata:x.x.x")
    implementation("com.github.bumptech.glide:glide:4.13.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    implementation ("androidx.compose.foundation:foundation:1.7.6")
```
- Trong MainActivity.kt chèn các thư viện:
```import android.os.Bundle
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
```
- Tạo các hàm:
  - Header(): xuất hiện thanh tìm kiếm
  - Promotions(): tạo những thẻ món ăn hot lên đầu trang
  - CategorySection(): tạo ra danh mục
  - BestSellerSection(): tạo ra phần những món ăn yêu thích
  - ProductDetailScreen(): tạo ra trang chi tiết sản phẩm
  - MyBottomBar(): tạo ra thanh công cụ ở phía dưới 
