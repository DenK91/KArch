package digital.neuron.karch.ui.questions

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import digital.neuron.karch.R
import digital.neuron.karch.data.model.Question
import digital.neuron.karch.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import javax.inject.Inject

class QuestionsActivity : BaseActivity(), QuestionsContract.View {

    @Inject lateinit var presenter: QuestionsPresenter
    private var adapter: QuestionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializePresenter()
        title = getString(R.string.android_tag)
        setupWidgets()
    }

    private fun initializePresenter() {
        DaggerQuestionsComponent.builder()
                .questionsPresenterModule(QuestionsPresenterModule(this))
                .questionRepositoryComponent(questionRepositoryComponent)
                .build()
                .inject(this)
    }

    private fun setupWidgets() {
        // Setup recycler view
        adapter = QuestionAdapter(mutableListOf())
        recyclerQuestion.layoutManager = LinearLayoutManager(this)
        recyclerQuestion.adapter = adapter
        recyclerQuestion.itemAnimator = DefaultItemAnimator()
        adapter?.itemClickListener({ _, pos -> presenter.getQuestion(adapter?.getItem(pos)!!.id)})

        // Refresh layout
        refresh.setOnRefreshListener { presenter.loadQuestions(true) }
        // Set notification text visible first
        textNotification.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.questions, menu)

        // Setup search widget in action bar
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.search(newText)
                return true
            }
        })

        return true
    }

    override fun showQuestions(questions: List<Question>) {
        textNotification.visibility = View.GONE
        adapter?.replaceData(questions)
    }

    override fun clearQuestions() {
        adapter?.clearData()
    }

    override fun showQuestionDetail(question: Question) {
        browse(question.link!!)
    }

    override fun stopLoadingIndicator() {
        if (refresh.isRefreshing) {
            refresh.isRefreshing = false
        }
    }

    override fun showNoDataMessage() = showNotification(getString(R.string.msg_no_data))
    override fun showErrorMessage(error: String) = showNotification(error)
    override fun showEmptySearchResult() = showNotification(getString(R.string.msg_empty_search_result))

    private fun showNotification(message: String) {
        textNotification.visibility = View.VISIBLE
        textNotification.text = message
    }
}
