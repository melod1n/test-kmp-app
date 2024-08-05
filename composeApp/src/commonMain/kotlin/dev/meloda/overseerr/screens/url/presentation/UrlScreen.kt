package dev.meloda.overseerr.screens.url.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.meloda.overseerr.screens.url.UrlViewModel
import dev.meloda.overseerr.screens.url.UrlViewModelImpl
import dev.meloda.overseerr.settings.SettingsController
import org.koin.compose.koinInject

class UrlScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val settingsController: SettingsController = koinInject()
        val viewModel: UrlViewModel = viewModel { UrlViewModelImpl(settingsController) }

        val screenState by viewModel.screenState.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Url") },
                    navigationIcon = {
                        IconButton(onClick = navigator::pop) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = screenState.url,
                    onValueChange = viewModel::onUrlInputChanged,
                    placeholder = { Text(text = "Url") },
                    isError = screenState.isWrongUrlError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Go,
                        keyboardType = KeyboardType.Uri
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = viewModel::onLoadButtonClicked,
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Text(text = "Load")
                    }

                    Button(
                        onClick = viewModel::onSaveButtonClicked,
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Text(text = "Save")
                    }

                    Button(
                        onClick = viewModel::onTestButtonClicked,
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Text(text = "Test")
                    }
                }
            }
        }
    }
}
