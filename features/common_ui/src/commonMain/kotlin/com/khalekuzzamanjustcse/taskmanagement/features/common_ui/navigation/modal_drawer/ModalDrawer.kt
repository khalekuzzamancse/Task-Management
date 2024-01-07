package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.modal_drawer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.NavigationGroup
import com.khalekuzzamanjustcse.taskmanagement.features.common_ui.navigation.NavigationItem


/*
Passing clicked group,
passing clicked item.
do don't need to pass group item group index when passing clicked item index
because parent already know which group is selected/clicked.
But what if the case,if all group item are shown at once,in that case we need to pass
group index and item index when passing which item is clicked
 */
data class NavGroupSelectedItem(
    val groupIndex: Int = -1,
    val itemIndex: Int = -1
)


@Composable
fun <T>DrawerSheet(
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    destinations: List<NavigationItem<T>>,
    destinationDecorator: @Composable (index: Int) -> Unit,
) {
    ModalDrawerSheet(
        modifier = Modifier,
    ) {
        LazyColumn(
            modifier = Modifier,
        ) {
            if (header != null) {
                item {
                    header()
                }
            }
                itemsIndexed(destinations) {index,_->
                    destinationDecorator(index)
                }
            if (footer != null) {
                item {
                    footer()
                }
            }
        }

    }

}

@Composable
fun DrawerSheet(
    header: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    groups: List<NavigationGroup>,
    groupDecorator: @Composable (Int) -> Unit,
    itemDecorator: @Composable (groupIndex: Int, index: Int) -> Unit,
) {
    ModalDrawerSheet(
        modifier = Modifier,
    ) {
        LazyColumn(
            modifier = Modifier,
        ) {
            if (header != null) {
                item {
                    header()
                }
            }
            groups.forEachIndexed { groupIndex, group ->
                item {
                    groupDecorator(groupIndex)
                }
                itemsIndexed(group.members) { index, _ ->
                    itemDecorator(groupIndex, index)
                }
            }
            if (footer != null) {
                item {
                    footer()
                }
            }
        }

    }

}



@Composable
fun ModalDrawer(
    modifier: Modifier,
    drawerState: DrawerState,
    sheet: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    AnimateVisibilityDecorator{
        ModalNavigationDrawer(
            modifier = modifier,
            drawerState = drawerState,
            drawerContent = sheet,
            content = content,
        )
    }

}
@Composable
fun AnimateVisibilityDecorator(
    content: @Composable () -> Unit
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val density = LocalDensity.current
    AnimatedVisibility(
        visibleState = state,
        enter = slideInHorizontally {
            with(density) { -400.dp.roundToPx() }
        },
        exit = slideOutHorizontally(),
    ) {
        content()
    }

}