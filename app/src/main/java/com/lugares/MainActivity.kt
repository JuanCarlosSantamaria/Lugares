package com.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lugares.databinding.ActivityMainBinding
import java.security.Principal

class MainActivity : AppCompatActivity() {
    //Definicion del objeto para hacer la autenticacion
    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //se inicializa el objeto para manejar las vistas..
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //se inicializa Firebase para usarse en el App
        //se asigna el objeto auth para hacer autenticacion
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btRegister.setOnClickListener {haceRegistro() }
        binding.btLogin.setOnClickListener {haceLogin() }


    } // Aca haremos algo en el video de la clase...

    private fun haceLogin() {
        //Recuperamos la informacion que ingreso el usuario
        val email = binding.etEmail.text.toString()
        val password = binding.etClave.text.toString()
        //Se llama a la funcion para crear un usuario en Firebase (email/password)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                    var user: FirebaseUser? = null
                    if (task.isSuccessful) { //Si se pudo crear el usuario
                        Log.d("Authenticating", "User created")
                        user = auth.currentUser   //Recupero la info del usuario creado
                        update(user)
                    } else {
                        Log.d("Authenticating", "Error authenticating user")
                        update(null)
                    }

                }

    }

    private fun haceRegistro() {
        //Recuperamos la informacion que ingreso el usuario
        val email = binding.etEmail.text.toString()
        val password = binding.etClave.text.toString()
        //Se llama a la funcion para crear un usuario en Firebase (email/password)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                    var user: FirebaseUser? = null
                    if (task.isSuccessful) { //Si se pudo crear el usuario
                        Log.d("Authenticating", "User created")
                        user = auth.currentUser   //Recupero la info del usuario creado
                    } else {
                        Log.d("Authenticating", "Error creating user")
                    }
                    update(user)
                }

    }

    private fun update(user: FirebaseUser?) {
        // Si hay un usuario definido... se pasa a la pantalla principal (Activity)
        if (user != null) {
            //Se pasa a la otra pantalla
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)

        }
    }

        //Se ejecuta cuando el app aparezca en la pantalla...
        public override fun onStart() {
            super.onStart()
            val usuario = auth.currentUser
            update(usuario)
        }


    }

