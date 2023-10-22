package com.khalekuzzamanjustcse.taskmanagement.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui.components.UserInfoCard


@Preview
@Composable
fun TaskScreen() {
    var showUser by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        if (showUser) {
            AssignUser(modifier = Modifier.matchParentSize()) {
                showUser = false
            }
        }
        else{
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Button(onClick = {
                    showUser = true
                }) {
                    Text(text = "Choose User")
                }
                InputField(label = "Title", onValueChange = {})
                InputFieldArea(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    label = "Description",
                    onValueChange = {
                    })
            }
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onTrailingIconClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = label)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            leadingIcon = null,
            trailingIcon = {
                trailingIcon?.let {
                    Icon(
                        modifier = Modifier.clickable {
                            onTrailingIconClick()
                        },
                        imageVector = trailingIcon,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation ?: VisualTransformation.None,
        )
    }
}

@Composable
fun InputFieldArea(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = label)
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
        )
    }
}

data class TaskAssignedUser(
    val name: String,
    val phone: String,
    val selected: Boolean,
)


@Composable
fun BoxScope.AssignUser(
    modifier: Modifier = Modifier,
    onCrossClick: () -> Unit = {},
) {
    var users by remember {
        mutableStateOf(
            listOf(
                TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813861", selected = false),
                TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813862", selected = false),
                TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813863", selected = false),
                TaskAssignedUser(name = "Mr Bean Karim", phone = "01738813864", selected = false),
            )
        )
    }
    val onLongClick: (Int) -> Unit = { index ->
        val user = users[index]
        val tmp = users.map { it }.toMutableList()
        tmp.removeAt(index)
        tmp.add(user.copy(selected = !user.selected))
        users = tmp
    }

    Column(modifier = modifier) {
        Button(onClick = onCrossClick) {
            Icon(imageVector = Icons.Filled.Cancel, contentDescription = null)
            Text(text = "Cancel")
        }
        users.forEachIndexed { i, it ->
            UserInfoCard(
                name = it.name,
                phone = it.phone,
                selected = it.selected,
                onLongClick = { onLongClick(i) }
            )
        }

    }

}
