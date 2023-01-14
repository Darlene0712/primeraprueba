package com.example.primeraprueba


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edUsuario: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnanadir: Button
    private lateinit var btnver: Button
    private lateinit var btnactualizar: Button


    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: UserAdapter? = null
    private var std: UsrModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        btnanadir.setOnClickListener { addUser() }
        btnver.setOnClickListener { getUser() }
        btnactualizar.setOnClickListener { updateUser() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.usuario, Toast.LENGTH_SHORT).show()
            edUsuario.setText(it.usuario)
            edEmail.setText(it.email)
            std = it
        }
        adapter?.setOnClickDeleteItem {
            deleteUser(it.id)
        }
    }



    private fun getUser() {
        val stdList = sqLiteHelper.getAllUsuario()
        Log.e("pppp", "${stdList.size}")

        adapter?.addItems(stdList)
    }

    private fun addUser() {
       val usuario = edUsuario.text.toString()
       val email = edEmail.text.toString()
        if (usuario.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Por favor ingrese lo requerido", Toast.LENGTH_SHORT).show()
        }else{
            val std = UsrModel(usuario = usuario, email = email)
            val status = sqLiteHelper.insertUsuario(std)
            if (status >-1){
                Toast.makeText(this,"Usuario Registrado",Toast.LENGTH_SHORT).show()
                clearEditText()
                getUser()
            }else{
                Toast.makeText(this,"datos no registrados",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateUser() {
        val  usuario = edUsuario.text.toString()
        val  email = edEmail.text.toString()


        if(usuario == std?.usuario && email == std?.email){
            Toast.makeText(this, "Cambio no guardado", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = UsrModel(id = std!!.id, usuario = usuario, email = email)
        val status = sqLiteHelper.updateUser(std)
        if(status > -1) {
            clearEditText()
            getUser()
        }else{
            Toast.makeText(this, "Actualizacion fallida", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteUser(id: Int){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Seguro deseas eliminarlo?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqLiteHelper.deleteUserByID(id)
            getUser()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }
    private fun clearEditText() {
        edUsuario.setText("")
        edEmail.setText("")
        edUsuario.requestFocus()
    }
    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edUsuario = findViewById(R.id.edUsuario)
        edEmail = findViewById(R.id.edEmail)
        btnanadir = findViewById(R.id.btnanadir)
        btnver = findViewById(R.id.btnver)
        btnactualizar = findViewById(R.id.btnactualizar)
        recyclerView = findViewById(R.id.recyclerView)
    }

}
