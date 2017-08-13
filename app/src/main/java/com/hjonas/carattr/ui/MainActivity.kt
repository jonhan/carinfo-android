package com.hjonas.carattr.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hjonas.carattr.R
import com.hjonas.carattr.utils.logMessage
import com.hjonas.data.ApiManager
import com.hjonas.data.services.carattributes.model.CarAttributes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchAttributes()
    }

    private fun fetchAttributes() {
        ApiManager.carInfoService().fetchCarAttributes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ handleAttributesFetched(it) }, { Toast.makeText(this, "FAIL!", Toast.LENGTH_LONG).show() })
    }

    private fun handleAttributesFetched(attributes: CarAttributes) {
        Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show()
        logMessage("handleAtributesFetched: $attributes")
    }
}
