package com.adrian.project.ui.signup.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.adrian.project.R
import com.adrian.project.ui.main.view.MainActivity
import com.adrian.project.ui.resetpasswordactivity.view.ResetPasswordActivity
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {

    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var btnSignIn: Button
    lateinit var btnSignUp: Button
    lateinit var btnResetPassword: Button
    lateinit var progressBar: ProgressBar
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        btnSignIn = findViewById<View>(R.id.sign_in_button) as Button
        btnSignUp = findViewById<View>(R.id.sign_up_button) as Button
        inputEmail = findViewById<View>(R.id.etEmail) as EditText
        inputPassword = findViewById<View>(R.id.etPassword) as EditText
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        btnResetPassword = findViewById<View>(R.id.btnResetPassword) as Button

        btnResetPassword.setOnClickListener { startActivity(Intent(this@SignupActivity, ResetPasswordActivity::class.java)) }

        btnSignIn.setOnClickListener { finish() }

        btnSignUp.setOnClickListener(View.OnClickListener {
            val email = inputEmail.text.toString().trim { it <= ' ' }
            val password = inputPassword.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(applicationContext, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            progressBar.visibility = View.VISIBLE
            //create user
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@SignupActivity) { task ->
                        Toast.makeText(this@SignupActivity, "createUserWithEmail:onComplete:" + task.isSuccessful, Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful) {
                            Toast.makeText(this@SignupActivity, "Authentication failed." + task.exception,
                                    Toast.LENGTH_SHORT).show()
                        } else {
                            startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                            finish()
                        }
                    }
        })
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
    }
}
