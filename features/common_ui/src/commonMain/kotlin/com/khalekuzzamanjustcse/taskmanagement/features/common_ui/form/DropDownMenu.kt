package com.khalekuzzamanjustcse.taskmanagement.features.common_ui.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun MyDropDownMenu(
    modifier: Modifier = Modifier,
    options: List<String>,
    isHorizontalOrientation: Boolean,
    selected: String,
    labelMinWidth: Dp,
    leadingIcon: ImageVector? = null,
    onOptionSelected: (String) -> Unit,
) {
    val properties by remember {
        mutableStateOf(
            FormTextFieldProperties(
                label = "Department",
                trailingIcon = Icons.Default.ArrowDropDown
            )
        )
    }


    if (isHorizontalOrientation) {
        Row(
            modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = properties.label, modifier = Modifier.defaultMinSize(
                    minWidth = labelMinWidth
                )
            )
            Spacer(Modifier.width(4.dp))
            MyDrop(
                options = options,
                selected = selected, onOptionSelected = onOptionSelected,
                leadingIcon = leadingIcon
            )
        }
    } else {
        Column(modifier) {
            Text(
                text = properties.label,
            )
            Spacer(Modifier.height(8.dp))
            MyDrop(
                options = options,
                selected = selected, onOptionSelected = onOptionSelected,
                leadingIcon = leadingIcon
            )
        }
    }
}

@Composable
private fun MyDrop(
    options: List<String>,
    leadingIcon: ImageVector? = null,
    selected: String,
    onOptionSelected: (String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Box {
        TextField(
            modifier =Modifier.onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
            value = selected,
            onValueChange = onOptionSelected,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        isExpanded = true
                    }
                )
            },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified
            ),
            shape = RoundedCornerShape(8.dp)
        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ,
            offset = DpOffset.Zero.copy(
                y=-((with(LocalDensity.current) { textFieldSize.height.toDp() }))
            )
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it)
                    },
                    onClick = {
                        onOptionSelected(it)
                        isExpanded = false
                    }
                )
            }
        }
    }


}
