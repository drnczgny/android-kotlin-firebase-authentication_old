package com.adrian.project.ui.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.adrian.project.R
import com.adrian.project.ui.main.view.MainActivity
import com.adrian.project.ui.resetpasswordactivity.view.ResetPasswordActivity
import com.adrian.project.ui.signup.view.SignupActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)

        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        btnSignup.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }
        })

        btnResetPassword.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
            }
        })

        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                    return
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                    return
                }

                progressBar.setVisibility(View.VISIBLE)

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this@LoginActivity, object : OnCompleteListener<AuthResult> {
                            override fun onComplete(task: Task<AuthResult>) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE)
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length < 6) {
                                        etPassword.error = getString(R.string.minimum_password)
                                    } else {
                                        Toast.makeText(this@LoginActivity, getString(R.string.auth_failed), Toast.LENGTH_LONG).show()
                                    }
                                } else {
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        })
            }
        })
    }
}