package com.stratpoint.weatherapp.presentation.auth.register.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.extensions.showToast
import com.stratpoint.weatherapp.navigation.Graph
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.ui.theme.spacing
import com.stratpoint.weatherapp.ui.views.button.CommonButton
import com.stratpoint.weatherapp.ui.views.dialog.BasicAlertDialog
import com.stratpoint.weatherapp.ui.views.dialog.ProgressDialog
import com.stratpoint.weatherapp.ui.views.textfield.CommonOutlinedTextField
import com.stratpoint.weatherapp.ui.views.textfield.PasswordOutlinedTextField
import com.stratpoint.weatherapp.ui.views.topbar.CommonBackTopBar

@Composable
fun RegisterScreen(
    navController: NavHostController,
    screenState: RegisterScreenState = rememberRegisterScreenState(),
    viewModel: RegisterViewModel = hiltViewModel()
) {

    InitializeScreenState(screenState, viewModel)

    Scaffold(
        topBar = {
            CommonBackTopBar(
                title = stringResource(id = R.string.label_create_account)
            ) {
                navController.navigateUp()
            }
        }
    ) {
        RegisterScreenContent(
            modifier = Modifier.padding(paddingValues = it),
            screenState = screenState,
            validateName = viewModel::validateName,
            validateEmail = viewModel::validateEmail,
            validatePassword = viewModel::validatePassword,
            onClickRegister = viewModel::register,
            onDismissErrorDialog = viewModel::dismissErrorDialog
        )
    }

    val isRegisterSuccessful = viewModel.isRegisterSuccessful.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = isRegisterSuccessful) {
        if (isRegisterSuccessful) {
            context.showToast(context.getString(R.string.message_register_success))
            navController.navigate(Graph.Home.route) {
                popUpTo(Graph.Auth.route) { inclusive = true }
            }
        }
    }

}

@Composable
fun RegisterScreenContent(
    modifier: Modifier,
    screenState: RegisterScreenState,
    validateName: (String) -> Unit,
    validateEmail: (String) -> Unit,
    validatePassword: (String) -> Unit,
    onClickRegister: () -> Unit,
    onDismissErrorDialog: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.large)
    ) {

        val (nameTextFieldRef, emailTextFieldRef, passwordTextFieldRef, registerButtonRef) = createRefs()

        NameTextField(
            modifier = Modifier
                .constrainAs(nameTextFieldRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = MaterialTheme.spacing.large)
                .fillMaxWidth(),
            screenState = screenState,
            validateName = validateName
        )

        EmailTextField(
            modifier = Modifier
                .constrainAs(emailTextFieldRef) {
                    top.linkTo(nameTextFieldRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            screenState = screenState,
            validateEmail = validateEmail
        )

        PasswordTextField(
            modifier = Modifier
                .constrainAs(passwordTextFieldRef) {
                    top.linkTo(emailTextFieldRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            screenState = screenState,
            validatePassword = validatePassword
        )

        RegisterButton(
            modifier = Modifier
                .constrainAs(registerButtonRef) {
                    top.linkTo(passwordTextFieldRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.medium),
            screenState = screenState,
            onClickRegister = onClickRegister
        )

    }

    LoadingDialog(screenState = screenState)
    ErrorDialog(
        screenState = screenState,
        onDismissErrorDialog = onDismissErrorDialog
    )
}

@Composable
fun NameTextField(
    modifier: Modifier,
    screenState: RegisterScreenState,
    validateName: (String) -> Unit
) {
    CommonOutlinedTextField(
        modifier = modifier,
        value = screenState.name.value,
        onValueChange = {
            validateName(it)
        },
        label = stringResource(id = R.string.label_name),
        errorMessage = if (!screenState.isValidName.value) {
            stringResource(id = R.string.error_name)
        } else null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun EmailTextField(
    modifier: Modifier,
    screenState: RegisterScreenState,
    validateEmail: (String) -> Unit
) {
    CommonOutlinedTextField(
        modifier = modifier,
        value = screenState.email.value,
        onValueChange = {
            validateEmail(it)
        },
        label = stringResource(id = R.string.label_email),
        errorMessage = if (!screenState.isValidEmail.value) {
            stringResource(id = R.string.error_invalid_email)
        } else null,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    screenState: RegisterScreenState,
    validatePassword: (String) -> Unit
) {
    PasswordOutlinedTextField(
        modifier = modifier,
        value = screenState.password.value,
        onValueChange = {
            validatePassword(it)
        },
        label = stringResource(id = R.string.label_password),
        errorMessage = if (!screenState.isValidPassword.value) {
            stringResource(id = R.string.error_password_length)
        } else null
    )
}

@Composable
fun RegisterButton(
    modifier: Modifier,
    screenState: RegisterScreenState,
    onClickRegister: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        label = stringResource(id = R.string.label_register),
        enabled = screenState.isRegisterButtonEnabled.value
    ) {
        onClickRegister()
    }
}

@Composable
fun LoadingDialog(screenState: RegisterScreenState) {
    val showDialog = screenState.isLoading.value

    if (showDialog) {
        ProgressDialog(
            showDialog = true,
            message = stringResource(id = R.string.progress_register)
        )
    }
}

@Composable
fun ErrorDialog(
    screenState: RegisterScreenState,
    onDismissErrorDialog: () -> Unit
) {
    val errorMessage = screenState.errorMessage.value

    errorMessage?.let {
        BasicAlertDialog(
            showDialog = true,
            title = stringResource(id = R.string.app_name),
            message = errorMessage,
            buttonLabel = stringResource(id = R.string.label_ok)
        ) {
            onDismissErrorDialog()
        }
    }
}

@Composable
private fun InitializeScreenState(
    screenState: RegisterScreenState,
    viewModel: RegisterViewModel
) {
    screenState.name.value = viewModel.name.collectAsState().value
    screenState.isValidName.value = viewModel.isValidName.collectAsState().value
    screenState.email.value = viewModel.email.collectAsState().value
    screenState.isValidEmail.value = viewModel.isValidEmail.collectAsState().value
    screenState.password.value = viewModel.password.collectAsState().value
    screenState.isValidPassword.value = viewModel.isValidPassword.collectAsState().value
    screenState.isRegisterButtonEnabled.value =
        viewModel.isRegisterButtonEnabled.collectAsState().value
    screenState.isLoading.value = viewModel.isLoading.collectAsState().value
    screenState.errorMessage.value = viewModel.errorMessage.collectAsState().value
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    WeatherAppTheme {
        RegisterScreenContent(
            modifier = Modifier,
            screenState = rememberRegisterScreenState(),
            validateName = {},
            validateEmail = {},
            validatePassword = {},
            onClickRegister = {},
            onDismissErrorDialog = {}
        )
    }

}