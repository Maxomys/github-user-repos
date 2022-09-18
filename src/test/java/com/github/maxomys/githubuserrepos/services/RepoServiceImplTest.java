package com.github.maxomys.githubuserrepos.services;

import com.github.maxomys.githubuserrepos.model.api.RepoDto;
import com.github.maxomys.githubuserrepos.model.client.Branch;
import com.github.maxomys.githubuserrepos.model.client.Commit;
import com.github.maxomys.githubuserrepos.model.client.Repo;
import com.github.maxomys.githubuserrepos.model.client.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepoServiceImplTest {

    @Mock
    RestClient restClient;

    RepoServiceImpl repoService;

    List<Repo> repoList;
    List<Branch> branchList;

    @BeforeEach
    void setUp() {
        repoService = new RepoServiceImpl(restClient);

        User user = User.builder()
                .login("username")
                .build();
        repoList = List.of(
                Repo.builder()
                        .owner(user)
                        .name("repo1")
                        .id(1L)
                        .fork(false)
                        .build(),
                Repo.builder()
                        .owner(user)
                        .name("repo2")
                        .id(2L)
                        .fork(true)
                        .build()
        );

        Commit commit = Commit.builder()
                .name("commit1")
                .sha("1")
                .build();
        branchList = List.of(
                Branch.builder()
                        .name("branch1")
                        .commit(commit)
                        .build()
        );
    }

    @Test
    void getNonForkReposForUsername() {
        when(restClient.getReposForUsernameAllPages(anyString())).thenReturn(repoList);
        when(restClient.getBranchesForRepoAllPages(anyString(), anyString())).thenReturn(branchList);

        List<RepoDto> repoDtos = repoService.getNonForkReposForUsername("user");

        verify(restClient).getReposForUsernameAllPages(anyString());
        verify(restClient).getBranchesForRepoAllPages(anyString(), anyString());

        assertNotNull(repoDtos);
        assertEquals(1, repoDtos.size());
        assertEquals("username", repoDtos.get(0).getOwnerLogin());
        assertEquals(1, repoDtos.get(0).getBranches().size());

    }

}
