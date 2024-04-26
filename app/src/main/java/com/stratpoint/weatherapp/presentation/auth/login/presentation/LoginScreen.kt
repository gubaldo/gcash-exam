package com.stratpoint.weatherapp.presentation.auth.login.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.stratpoint.weatherapp.R
import com.stratpoint.weatherapp.extensions.showToast
import com.stratpoint.weatherapp.navigation.AuthScreen
import com.stratpoint.weatherapp.navigation.Graph
import com.stratpoint.weatherapp.ui.theme.WeatherAppTheme
import com.stratpoint.weatherapp.ui.theme.spacing
import com.stratpoint.weatherapp.ui.views.button.CommonButton
import com.stratpoint.weatherapp.ui.views.dialog.BasicAlertDialog
import com.stratpoint.weatherapp.ui.views.dialog.ProgressDialog
import com.stratpoint.weatherapp.ui.views.textfield.CommonOutlinedTextField
import com.stratpoint.weatherapp.ui.views.textfield.PasswordOutlinedTextField

@Composable
fun LoginScreen(
    navController: NavHostController,
    screenState: LoginScreenState = rememberLoginScreenState(),
    viewModel: LoginViewModel = hiltViewModel()
) {

    InitializeScreenState(screenState, viewModel)

    LoginScreenContent(
        screenState = screenState,
        validateEmail = viewModel::validateEmail,
        validatePassword = viewModel::validatePassword,
        onClickLogin = viewModel::login,
        onClickSignUp = {
            navController.navigate(AuthScreen.Register.route)
        },
        onDismissErrorDialog = viewModel::dismissErrorDialog
    )

    val isLoginSuccessful = viewModel.isLoginSuccessful.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = isLoginSuccessful) {
        if (isLoginSuccessful) {
            context.showToast(context.getString(R.string.message_login_success))
            navController.navigate(Graph.Home.route) {
                popUpTo(Graph.Auth.route) { inclusive = true }
            }
        }
    }

}

@Composable
fun LoginScreenContent(
    screenState: LoginScreenState,
    validateEmail: (String) -> Unit,
    validatePassword: (String) -> Unit,
    onClickLogin: () -> Unit,
    onClickSignUp: () -> Unit,
    onDismissErrorDialog: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.large)
    ) {

        val (appNameTextRef, emailTextFieldRef, passwordTextFieldRef, loginButtonRef, createAnAccountTextRef) = createRefs()

        AppNameText(
            modifier = Modifier
                .constrainAs(appNameTextRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(
                    top = MaterialTheme.spacing.extraLarge,
                    bottom = MaterialTheme.spacing.large
                )
        )

        EmailTextField(
            modifier = Modifier
                .constrainAs(emailTextFieldRef) {
                    top.linkTo(appNameTextRef.bottom)
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

        LoginButton(
            modifier = Modifier
                .constrainAs(loginButtonRef) {
                    top.linkTo(passwordTextFieldRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.medium),
            screenState = screenState,
            onClickLogin = onClickLogin
        )

        CreateAnAccountText(
            modifier = Modifier
                .constrainAs(createAnAccountTextRef) {
                    top.linkTo(loginButtonRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClickSignUp = onClickSignUp
        )

    }

    LoadingDialog(screenState = screenState)
    ErrorDialog(
        screenState = screenState,
        onDismissErrorDialog = onDismissErrorDialog
    )

}

@Composable
fun AppNameText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    screenState: LoginScreenState,
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
    screenState: LoginScreenState,
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
fun LoginButton(
    modifier: Modifier = Modifier,
    screenState: LoginScreenState,
    onClickLogin: () -> Unit
) {
    CommonButton(
        modifier = modifier,
        label = stringResource(id = R.string.label_login),
        enabled = screenState.isLoginButtonEnabled.value
    ) {
        onClickLogin()
    }
}

@Composable
fun CreateAnAccountText(
    modifier: Modifier = Modifier,
    onClickSignUp: () -> Unit
) {
    Text(
        modifier = modifier
            .clickable {
                onClickSignUp()
            },
        text = stringResource(id = R.string.label_create_account),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun LoadingDialog(screenState: LoginScreenState) {
    val showDialog = screenState.isLoading.value

    if (showDialog) {
        ProgressDialog(
            showDialog = true,
            message = stringResource(id = R.string.progress_login)
        )
    }
}

@Composable
fun ErrorDialog(
    screenState: LoginScreenState,
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
    screenState: LoginScreenState,
    viewModel: LoginViewModel
) {
    screenState.email.value = viewModel.email.collectAsState().value
    screenState.isValidEmail.value = viewModel.isValidEmail.collectAsState().value
    screenState.password.value = viewModel.password.collectAsState().value
    screenState.isValidPassword.value = viewModel.isValidPassword.collectAsState().value
    screenState.isLoginButtonEnabled.value = viewModel.isLoginButtonEnabled.collectAsState().value
    screenState.isLoading.value = viewModel.isLoading.collectAsState().value
    screenState.errorMessage.value = viewModel.errorMessage.collectAsState().value
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    WeatherAppTheme {
        LoginScreenContent(
            screenState = rememberLoginScreenState(),
            validateEmail = {},
            validatePassword = {},
            onClickLogin = {},
            onClickSignUp = {},
            onDismissErrorDialog = {}
        )
    }

}