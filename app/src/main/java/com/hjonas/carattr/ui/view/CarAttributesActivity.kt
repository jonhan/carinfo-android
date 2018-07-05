package com.hjonas.carattr.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.hjonas.carattr.R
import com.hjonas.carattr.ui.CarAttributesContract
import com.hjonas.carattr.ui.attributeslist.AttributeSectionsProvider
import com.hjonas.carattr.ui.attributeslist.CarAttributeItem
import com.hjonas.carattr.ui.attributeslist.CarInfoRecyclerAdapter
import com.hjonas.carattr.ui.attributeslist.viewholder.ViewHolderFactory
import com.hjonas.carattr.ui.presenter.CarAttributesPresenter
import com.hjonas.carattr.utils.setVisible
import com.hjonas.data.ApiManager
import kotlinx.android.synthetic.main.activity_car_attributes.*
import kotlinx.android.synthetic.main.error_info.*

class CarAttributesActivity : AppCompatActivity(), CarAttributesContract.View {

  companion object {
    private const val INTENT_EXTRA_VIN = "intent.extra.vin"

    /**
     * Creates and returns an [Intent] that can be used to start this activity with a vin-parameter for the car to show attributes for
     */
    fun newIntent(context: Context, vin: String) =
        Intent(context, CarAttributesActivity::class.java).apply {
          putExtra(INTENT_EXTRA_VIN, vin)
        }
  }

  private lateinit var presenter: CarAttributesContract.Presenter
  private val recyclerAdapter = CarInfoRecyclerAdapter(ViewHolderFactory())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_car_attributes)

    presenter = CarAttributesPresenter(this,
                                       intent?.getStringExtra(INTENT_EXTRA_VIN),
                                       AttributeSectionsProvider(),
                                       ApiManager.carAttributesService)
    recyclerView.apply {
      layoutManager = LinearLayoutManager(this@CarAttributesActivity, LinearLayoutManager.VERTICAL, false)
      adapter = recyclerAdapter
    }

    configListeners()
    presenter.subscribe()
  }

  private fun configListeners() {
    errorInfoRetryButton.setOnClickListener {
      errorInfoLayout.setVisible(false)
      presenter.fetchData()
    }
  }

  override fun onDestroy() {
    presenter.unsubscribe()
    super.onDestroy()
  }

  override fun showLoading() {
    loadingProgressBar.setVisible(true)
  }

  override fun hideLoading() {
    loadingProgressBar.setVisible(false)
  }

  override fun showAttributeSections(sections: List<CarAttributeItem>) {
    recyclerAdapter.setItems(sections)
  }

  override fun showConnectionProblemError() {
    errorInfoLayout.setVisible(true)
    errorInfoIcon.setImageResource(R.drawable.ic_cloud_off)
    errorInfoMessageTv.text = getString(R.string.error_connection_problem)
  }

  override fun showIncorrectResponseError(code: Int) {
    errorInfoLayout.setVisible(true)
    errorInfoIcon.setImageResource(R.drawable.ic_error)
    errorInfoMessageTv.text = if (code == CarAttributesContract.CODE_UNKNOWN_ERROR) {
      getString(R.string.error_unknown)
    }
    else {
      getString(R.string.error_incorrect_response, code)
    }
  }
}
