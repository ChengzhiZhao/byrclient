package haitong.yao.byrclient;

import haitong.yao.byrclient.adapters.ToptenListAdapter;
import haitong.yao.byrclient.models.Article;
import haitong.yao.byrclient.tasks.AbsTask;
import haitong.yao.byrclient.tasks.GetArticlesTask;
import haitong.yao.byrclient.tasks.ITaskFinishListener;
import haitong.yao.byrclient.utils.BYRToast;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RecommendedActivity extends NoTitleActivity implements
        OnItemClickListener, ITaskFinishListener {

    private final int DIVIDER_HEIGHT = 6;

    private ListView mToptenList;
    private ToptenListAdapter mListAdapter;
    private View mLoadingView;

    private Context mContext;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.act_recommended);
        mContext = getApplicationContext();
        findViewsById();
        initAdapter();
        setListeners();
        getArticles();
    }

    @Override
    protected void findViewsById() {
        mToptenList = (ListView) findViewById(R.id.recommended_list);
        mToptenList.setDivider(null);
        mToptenList.setDividerHeight(DIVIDER_HEIGHT);
        mLoadingView = findViewById(R.id.loading_view);
    }

    private void initAdapter() {
        mListAdapter = new ToptenListAdapter(mContext);
        mToptenList.setAdapter(mListAdapter);
    }

    @Override
    protected void setListeners() {
        mToptenList.setOnItemClickListener(this);
    }

    private void getArticles() {
        new GetArticlesTask(mContext, "recommended", this).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }

    @Override
    public void onTaskFinished(AbsTask task, Object result) {

        mLoadingView.setVisibility(View.GONE);

        if (null == result) {
            BYRToast.showLongToast(mContext, R.string.fail_get_content);
        } else {
            mToptenList.setVisibility(View.VISIBLE);
            mListAdapter.setContent((ArrayList<Article>) result);
            mListAdapter.notifyDataSetChanged();
        }

    }

}
