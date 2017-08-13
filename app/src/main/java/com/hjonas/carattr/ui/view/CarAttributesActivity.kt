package com.hjonas.carattr.ui.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.ui.presenter.CarAttributesPresenter
import kotlinx.android.synthetic.main.activity_car_attributes.*

class CarAttributesActivity : AppCompatActivity(), CarAttributesContract.View {

    lateinit var presenter: CarAttributesContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_attributes)

        presenter = CarAttributesPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        presenter.unsubscribe()
        super.onPause()
    }

    override fun showLoading() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingProgressBar.visibility = View.GONE
    }

    override fun connectionProblemError() {
        Toast.makeText(this, "Connection progrem", Toast.LENGTH_LONG).show()
    }

    override fun incorrectResponseError(code: Int) {
        Toast.makeText(this, "Error response: $code", Toast.LENGTH_LONG).show()
    }
}
