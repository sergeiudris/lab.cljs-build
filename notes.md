- git rewriting history, rebase
- https://git-scm.com/book/en/v2/Git-Tools-Rewriting-History

- git change commit dates after rabase to author's

git filter-branch --env-filter 'GIT_COMMITTER_DATE=$GIT_AUTHOR_DATE; export GIT_COMMITTER_DATE'

- rebase from the beginning

git rebase -i --root

- git filter-repo
- https://htmlpreview.github.io/?https://github.com/newren/git-filter-repo/blob/docs/html/git-filter-repo.html#EXAMPLES