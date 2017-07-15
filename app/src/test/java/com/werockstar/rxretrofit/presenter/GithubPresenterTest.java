package com.werockstar.rxretrofit.presenter;

import com.werockstar.rxretrofit.api.GithubAPI;
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

    @Mock GithubPresenter.View view;
    @Mock GithubAPI api;

    private GithubPresenter presenter;

    @Rule
    public RxSchedulerRule rxSchedulerRule = new RxSchedulerRule();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GithubPresenter(view, api);
    }

    @Test
    public void presenter_should_be_not_null() throws Exception {
        assertNotNull(presenter);
    }

    @Test
    public void request_github_information_should_show_github_information() throws Exception {
        String githubUser = "WeRockStar";
        GithubCollection github = new GithubCollection();

        when(api.getGithubInfo(githubUser)).thenReturn(Observable.just(github));

        presenter.getGithubInfo(githubUser);

        verify(view).showGithubInfo(github);
        verify(view).onCompleted();
    }

    @Test
    public void request_github_information_should_see_error() throws Exception {
        Throwable exception = new Throwable();

        when(api.getGithubInfo("")).thenReturn(Observable.error(exception));

        presenter.getGithubInfo("");

        verify(view).onError(exception);
    }
}