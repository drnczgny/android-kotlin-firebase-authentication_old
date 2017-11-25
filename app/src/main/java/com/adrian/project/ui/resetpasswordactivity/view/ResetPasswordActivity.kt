package com.adrian.project.ui.resetpasswordactivity.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.adrian.project.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class ResetPasswordActivity : AppCompatActivity() {

    lateinit var inputEmail: EditText
    lateinit var btnReset: Button
    lateinit var btnBack: Button
    lateinit var auth: FirebaseAuth
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        inputEmail = findViewById<View>(R.id.etEmail) as EditText
        btnReset = findViewById<View>(R.id.btnResetPassword) as Button
        btnBack = findViewById<View>(R.id.btn_back) as Button
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar

        auth = FirebaseAuth.getInstance()

        btnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                finish()
            }
        })

        btnReset.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {

                val email = inputEmail.text.toString().trim { it <= ' ' }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(application, "Enter your registered email id", Toast.LENGTH_SHORT).show()
                    return
                }

                progressBar.setVisibility(View.VISIBLE)
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(object : OnCompleteListener<Void> {
                            override fun onComplete(task: Task<Void>) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(this@ResetPasswordActivity, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this@ResetPasswordActivity, "Failed to send reset email!", Toast.LENGTH_SHORT).show()
                                }

                                progressBar.setVisibility(View.GONE)
                            }
                        })
            }
        })
    }

}
