package com.FrogDomo.View.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.FrogDomo.R
import com.FrogDomo.View.Main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import com.FrogDomo.api.ApiClient

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_login)

        val buttonSeConnecter = findViewById<Button>(R.id.bt_SeConnecter)
        val buttonCreationCompte = findViewById<Button>(R.id.bt_CreationCompte)

        val inputAPI = findViewById<TextInputEditText>(R.id.textInputChgAPIMdp)
        val inputEmail = findViewById<TextInputEditText>(R.id.textInputChgMdp)
        val inputPassword = findViewById<TextInputEditText>(R.id.textInputChgConMdp)

        val inputLayoutApiDomaine = findViewById<TextInputLayout>(R.id.TinputLayoutApiDomaine)
        val inputLayoutUsernameEmail = findViewById<TextInputLayout>(R.id.TinputLayoutUserNameEmail)
        val inputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutChgConMdp)

        buttonSeConnecter.setOnClickListener {

            if (inputAPI.text.toString().isEmpty()) {
                inputLayoutApiDomaine.error = "Le domaine API ne peut pas être vide"
            } else {
                inputLayoutApiDomaine.error = null // Effacez l'erreur si le champ est valide
            }

            if (inputEmail.text.toString().isEmpty()) {
                inputLayoutUsernameEmail.error = "Le username/Email ne peut pas être vide"
            } else {
                inputLayoutUsernameEmail.error = null // Effacez l'erreur si le champ est valide
            }

            if (inputPassword.text.toString().isEmpty()) {
                inputLayoutPassword.error = "Le mot de passe ne peut pas être vide"
            } else {
                inputLayoutPassword.error = null // Effacez l'erreur si le champ est valide
            }

            if (inputEmail.text.toString().isNotEmpty() && inputPassword.text.toString().isNotEmpty() && inputPassword.text.toString().isNotEmpty()) {
                ApiClient.setUrl(inputAPI.text.toString())
                loginViewModel.loginUser(inputEmail.text.toString(), inputPassword.text.toString())
                loginViewModel.loginResult.observe(this) { loginSuccess ->
                    if (loginSuccess) {
                        Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                        // navigate to main activity
                        finish()
                    } else {
                        Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
