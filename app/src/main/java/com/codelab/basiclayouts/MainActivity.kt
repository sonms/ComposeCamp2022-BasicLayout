/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelab.basiclayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MySootheApp() }
    }
}

// Step: Search bar - Modifiers
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    //구성요소 구현(searchbar 기본 구성요소 아마 edittext?
    TextField(
        value = "", //하드 코딩
        onValueChange = {},// 콜백
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier//수정자로 모든 디자인, 느낌 동작 제어
            //.fillMaxHeight() layout이 너무 늘어남
            .heightIn(min = 56.dp)
    )
}

// Step: Align your body - Alignment
@Composable
fun AlignYourBodyElement(
    //이미지와 텍스트를 동적
    @DrawableRes drawable: Int,
    @StringRes text : Int,
    modifier: Modifier = Modifier
) {
    //image, text 컴포저블이 필요, 세로방향 - column
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(painter = painterResource(id = R.drawable.ab1_inversions),
            //그냥 장식용이라 null로 설정
            contentDescription = null,
            //이미지의 사이즈 조절 (현재는 둥근형태, fit, fillbounds 등이 있다)
            contentScale = ContentScale.Crop,
            //수정자를 불러와 size와 clip으로 형태(shape)조절
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(id = R.string.ab1_inversions),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.paddingFromBaseline(
                top = 24.dp, bottom = 8.dp
            ),

        )

    }

}

// Step: Favorite collection card - Material Surface
@Composable
fun FavoriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text : Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(192.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fc2_nature_meditations),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(56.dp)
            )
            Text(
                text = stringResource(id = R.string.fc2_nature_meditations),
                style = MaterialTheme.typography.h3,
                //modifier = Modifier.padding(8.dp)
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// Step: Align your body row - Arrangements
@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier
) {
    //스크롤 가능한 행을 구현(LazyRow)
    LazyRow(
        //spacedBy:항목 사이에 8dp 간격추가
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        //상위 목록의 경계 내에서 콘텐츠를 자르지 않고 스크롤할 수 있음
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        //lazy목록, AlignYourBodyElement 컴포저블을 내보냄
        items(alignYourBodyData) { item ->
            AlignYourBodyElement(drawable = item.drawable, text = item.text)
        }
    }
}

// Step: Favorite collections grid - LazyGrid
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    //column도 있지만 그리드 요소 매핑의 효과적인 LazyHorizontalGrid
    LazyHorizontalGrid(
        //두 개의 고정 행
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        //수평
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        //수직
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(favoriteCollectionsData) { item ->
            FavoriteCollectionCard(
                drawable = item.drawable,
                text = item.text,
                modifier = Modifier.height(56.dp)
            )
        }
    }
}

// Step: Home section - Slot APIs
@Composable
fun HomeSection(
    @StringRes title : Int,
    //@Composable 호출은 @Composable 함수의 컨텍스트에서만 발생할 수 있어서
    //@Composable이 먼저 선언되면 안됨
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    //title, content 형태의 Column
    Column(modifier) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h2,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

// Step: Home screen - Scrolling
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: Bottom navigation - Material
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    // Implement composable here
}

// Step: MySoothe App - Scaffold
@Composable
fun MySootheApp() {
    // Implement composable here
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchBarPreview() {
    MySootheTheme { SearchBar(Modifier.padding(8.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyElementPreview() {
    MySootheTheme {
        AlignYourBodyElement(
            modifier = Modifier.padding(8.dp),
            text = R.string.ab1_inversions,
            drawable = R.drawable.ab1_inversions
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionCardPreview() {
    MySootheTheme {
        FavoriteCollectionCard(
            modifier = Modifier.padding(8.dp),
            text = R.string.fc2_nature_meditations,
            drawable = R.drawable.fc2_nature_meditations
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionsGridPreview() {
    MySootheTheme { FavoriteCollectionsGrid() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyRowPreview() {
    MySootheTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun HomeSectionPreview() {
    MySootheTheme {
        HomeSection(R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ScreenContentPreview() {
    MySootheTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BottomNavigationPreview() {
    MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePreview() {
    MySootheApp()
}
