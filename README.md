# Weaver-testonly #

A minimal scala project to investigate Weaver testOnly resource duplication.


Running the tests using `testOnly` where it matches multiple suites leads to shared resources being initialised multiple times.

Run tests with:

```
sbt test
```

Output:

```
weaver-resource-cleanup:main> test
========> creating base resource
[info] acme.weaverissue.SharingSuite
[info] + a stranger, from the outside ! ooooh 23ms
[info] acme.weaverissue.OtherSharingSuite
[info] + oops, forgot something here 1ms
[info] Passed: Total 2, Failed 0, Errors 0, Passed 2
[success] Total time: 2 s, completed 17 Feb. 2022, 5:49:52 pm
```

We can see that the shared resource is created once.

Run tests using testOnly with:

```
sbt testOnly *SharingSuites
```

Output:

```
weaver-resource-cleanup:main> testOnly *SharingSuite
[info] acme.weaverissue.SharingSuite
========> creating base resource
[info] + a stranger, from the outside ! ooooh 26ms
========> creating base resource
[info] acme.weaverissue.OtherSharingSuite
[info] + oops, forgot something here 1ms
[info] Passed: Total 2, Failed 0, Errors 0, Passed 2
[success] Total time: 1 s, completed 17 Feb. 2022, 5:50:05 pm
```

We can see that the share resource is created twice. (actually x times where x is the number of suites matched against using testOnly).

Expected:

```
weaver-resource-cleanup:main> testOnly *SharingSuite
========> creating base resource       <---- only created once
[info] acme.weaverissue.SharingSuite
[info] + a stranger, from the outside ! ooooh 26ms
[info] acme.weaverissue.OtherSharingSuite
[info] + oops, forgot something here 1ms
[info] Passed: Total 2, Failed 0, Errors 0, Passed 2
[success] Total time: 1 s, completed 17 Feb. 2022, 5:50:05 pm
```