package com.werockstar.rxretrofit.presenter;

import com.werockstar.rxretrofit.manager.service.GithubAPI;
import com.werockstar.rxretrofit.model.GithubCollection;
import com.werockstar.rxretrofit.util.RxSchedulerRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class GithubPresenterTest {

    @Mock
    GithubPresenter.View view;
    @Mock
    GithubAPI api;
    GithubPresenter presenter;

    @Rule
    public RxSchedulerRule rxSchedulerRule = new RxSchedulerRule();

    GithubCollection collection;
    private final String USERNAME = "WeRockStar";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GithubPresenterImpl(view, api);
        collection = new GithubCollection();
    }

    @Test
    public void presenter_should_be_not_null() throws Exception {
        assertNotNull(presenter);
    }

    @Test
    public void should_see_github_infomations() throws Exception {
        when(api.getGithubInfo(USERNAME)).thenReturn(Observable.just(collection));
        presenter.getGithubInfo(USERNAME);
        verify(view).showGithubInfo(collection);
        verify(view).onCompleted();
    }

    @Test
    public void should_see_error() throws Exception {
        Throwable exception = new Throwable();
        when(api.getGithubInfo(USERNAME)).thenReturn(Observable.error(exception));
        presenter.getGithubInfo(USERNAME);
        verify(view).onError(exception);
    }
}