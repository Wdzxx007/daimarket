package com.jishi.daichao.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.jishi.daichao.R;
import com.jishi.daichao.app.ActivityManager;
import com.trello.rxlifecycle4.components.support.RxFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

/**
 * Created by laulee on 17/3/23.
 */

public abstract class BaseActivity extends RxFragmentActivity {

    @Nullable
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;

    @Nullable
    @BindView(R.id.tv_toolbar_left)
    TextView tvBack;

    @Nullable
    @BindView(R.id.tv_toolbar_right)
    TextView tvRight;

    @Nullable
    @BindView(R.id.wv_close_view)
    View closeView;

    private Unbinder unbinder;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( setContentViewId( ) );
        unbinder = ButterKnife.bind( this );
        if( hasToolBar( ) ) {
            initToolbarView( );
        }
        //将activity加入到集合中
        ActivityManager.addActivity( this );
        if( webviewBackShow( ) ) {
            closeView.setVisibility( View.VISIBLE );
        }
        if (webviewBackShow()) {
            findViewById(R.id.wv_close_view).setVisibility(View.VISIBLE);
            findViewById(R.id.wv_close_view).setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick( View v ) {
                    doWebViewBack();
                }
            } );
        }
        initParams( );
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white_background).fitsSystemWindows(true).init();
    }

    /**
     * 是否含有toolbar
     *
     * @return
     */
    protected boolean hasToolBar() {
        return true;
    }

    /**
     * 获取toolbar view
     */
    private void initToolbarView() {
        tvTitle = (TextView) findViewById( R.id.tv_toolbar_title );
        tvBack = (TextView) findViewById( R.id.tv_toolbar_left );
        closeView = findViewById( R.id.wv_close_view );
        tvRight = (TextView) findViewById( R.id.tv_toolbar_right );
        tvBack.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick( View v ) {
                doBack( );
            }
        } );
        tvRight.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick( View v ) {
                doMore( );
            }
        } );
        closeView.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick( View v ) {
                finish();
            }
        } );
    }

    /**
     * 网页activity关闭按钮
     *
     * @return
     */
    protected boolean webviewBackShow() {
        return false;
    }

    @Optional
    @OnClick({ R.id.tv_toolbar_left, R.id.tv_toolbar_right, R.id.wv_close_view })
    public void onClick( View view ) {
        if( view == null ) {
            return;
        }
        if( tvBack != null && tvRight != null ) {
            int i = view.getId( );
            if( i == R.id.tv_toolbar_left ) {
                doBack( );

            } else if( i == R.id.tv_toolbar_right ) {
                doMore( );

            } else if( i == R.id.wv_close_view ) {
                doWebViewBack( );

            }
        }
    }

    /**
     * 网页关闭事件
     */
    protected void doWebViewBack() {

    }

    /**
     * 右上角按钮
     */
    protected void doMore() {
    }

    /**
     * 返回按钮
     */
    protected void doBack() {
        finish( );
    }

    /**
     * 初始化参数
     */
    protected abstract void initParams();

    //设置布局界面ID
    protected abstract int setContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy( );
        if( unbinder != null )
            unbinder.unbind( );
        ActivityManager.removeActivity( this );
    }

    /**
     * @param moreTitle
     */
    protected void setMoreTitle( String moreTitle ) {
        if( tvRight != null ) {
            tvRight.setVisibility( View.VISIBLE );
            tvRight.setText( moreTitle );
        }
    }

    /**
     * 返回文字
     *
     * @param backText
     */
    protected void setLeftText( String backText ) {
        if( tvBack != null ) {
            tvBack.setText( backText );
        }
    }

    /**
     * title
     *
     * @param tilte
     */
    protected void setTitle( String tilte ) {
        if( tvTitle != null ) {
            tvTitle.setText( tilte );
        }
    }
}
