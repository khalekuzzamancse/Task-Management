package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.search_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.MySearchBar
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.list.GenericListScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SearchDecoratorState<T>(
    private val items: List<T>,
    val predicate: (T, String) -> Boolean,
) {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()
    private val _results = MutableStateFlow(items)
    val results = _results.asStateFlow()
    fun onQueryChanged(query: String) {
        _query.update { query }
        _results.value = results.value.filter { predicate(it, query) }
        if (query == "") {
            _results.value = items
        }
    }

    private val _active = MutableStateFlow(true)
    val active = _active.asStateFlow()
    fun onActiveChanged(status: Boolean) {
        _active.value = status

    }

    private val _showSearch = MutableStateFlow(false)
    val showSearch = _showSearch.asStateFlow()
    fun onSearchChanged(status: Boolean) {
        _showSearch.value = status
        if (!status) {
            clear()
        }
    }

    private fun clear() {
        _results.value = items
        _query.value = ""

    }

}

@Composable
fun <T> SearchDecorator(
    items: List<T>,
    predicate: (T, String) -> Boolean,
    navigationIcon: ImageVector? = null,
    onNavigationClick: (() -> Unit)? = null,
    itemDecorator: @Composable (T, highLightedText: String) -> Unit,
    bottomNavbar:@Composable () -> Unit={},
    content: @Composable (Modifier) -> Unit,
) {
    val uiState = remember {
        SearchDecoratorState(
            items,
            predicate
        )
    }
    SearchDecorator(
        query = uiState.query.collectAsState().value,
        onQueryChanged = uiState::onQueryChanged,
        active = uiState.active.collectAsState().value,
        onActiveChanged = uiState::onActiveChanged,
        showSearch = uiState.showSearch.collectAsState().value,
        onShowSearchChanged = uiState::onSearchChanged,
        result = uiState.results.collectAsState().value,
        navigationIcon = navigationIcon,
        onNavigationClick = onNavigationClick,
        itemDecorator = itemDecorator,
        contentOnNoSearch = content,
        bottomNavbar = bottomNavbar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchDecorator(
    showSearch: Boolean,
    onShowSearchChanged: (Boolean) -> Unit,
    query: String,
    onQueryChanged: (String) -> Unit,
    active: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    result: List<T>,
    navigationIcon: ImageVector? = null,
    onNavigationClick: (() -> Unit)? = null,
    itemDecorator: @Composable (T, String) -> Unit,
    bottomNavbar: @Composable () -> Unit={},
    contentOnNoSearch: @Composable (Modifier) -> Unit,
) {

    Box {
        if (!showSearch) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            if (navigationIcon != null) {
                                IconButton(
                                    onClick = {
                                        onNavigationClick?.invoke()
                                    }) {
                                    Icon(
                                        imageVector = navigationIcon,
                                        contentDescription = null
                                    )
                                }
                            }

                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    onShowSearchChanged(true)
                                    onActiveChanged(true)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp)
                        )
                    )

                },
                bottomBar = bottomNavbar
            ) {
                contentOnNoSearch(Modifier.padding(it))
            }
        } else {
            MySearchBar(
                query = query,
                onQueryChange = onQueryChanged,
                active = active,
                onActiveChanged = onActiveChanged,
                modifier = Modifier,
                onGoBack = {
                    onShowSearchChanged(false)
                },

                ) {
                GenericListScreen(
                    modifier = Modifier,
                    items = result,
                    itemDecorator = { _, item ->
                        itemDecorator(item, query)
                    }
                )
            }
        }

    }


}

