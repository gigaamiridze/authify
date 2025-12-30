## Authify Release Process

This document outlines the step-by-step process for releasing a new version of Authify using the Maven Release Plugin.

### Prerequisites

Before starting the release process, ensure you have:

- Write access to the repository
- All pending changes committed and pushed to the main branch
- Clean working directory (verify with `git status`)

### Version Numbering

Authify follows [Semantic Versioning](https://semver.org/):

- **MAJOR.MINOR.PATCH** (e.g., 1.0.0)
    - **MAJOR**: Breaking changes
    - **MINOR**: New features 
    - **PATCH**: Bug fixes

Development versions use the `-SNAPSHOT` suffix (e.g., 1.1.0-SNAPSHOT).

### Release Steps

#### 1. Determine Version Numbers

Decide on two version numbers:

- **Release version**: The version to be released (e.g., `1.0.0`)
- **Next development version**: The version for ongoing development (e.g., `1.1.0-SNAPSHOT`)

#### 2. Prepare the Release (Dry Run)

First, run a dry run to verify the release process without making any changes:

```sh
mvn release:prepare -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.1.0-SNAPSHOT -DdryRun=true
```

This will:
- Validate that there are no SNAPSHOT dependencies
- Check that the working directory is clean
- Simulate version updates in pom.xml
- Show what commits and tags would be created

Review the output carefully. If everything looks correct, run `mvn release:clean` and proceed to the actual release preparation.

#### 3. Prepare the Release

Execute the actual release preparation:

```sh
mvn release:prepare -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.1.0-SNAPSHOT
```

This command will:
- Remove `-SNAPSHOT` from the version in pom.xml
- Commit the release version
- Create a Git tag (e.g., `authify-1.0.0-RELEASE`)
- Update pom.xml to the next development version
- Commit the development version

The commits and tags are created locally but not automatically pushed to the remote repository.

#### 4. Perform the Release

Build and deploy the release artifacts from the tagged version:

```sh
mvn release:perform
```

This will:
- Check out the code from the release tag
- Build the project

#### 5. Verify the Release

Before pushing to the remote repository:

- Check that the artifacts were deployed correctly
- Verify the release tag exists: `git tag -l`
- Review the release commits: `git log`
- Ensure the version in pom.xml is the expected development version

#### 6. Push Commits and Tags

After verifying everything locally, push the commits and tags to the remote repository:

```sh
git push origin main
git push origin --tags
```

Alternatively, push both in a single command:

```sh
git push origin main --follow-tags
```

### Rollback Procedure

If something goes wrong during the release process (e.g., failed tests, incorrect version), you can rollback:

```sh
mvn release:rollback
```

This will:
- Delete the release tag
- Revert the release commits
- Restore the project to its pre-release state

Rollback only works if you haven't pushed to the remote repository yet. If commits and tags are already pushed, you'll need to manually revert them.

### Best Practices

- Always run a dry run first to catch issues early
- Perform releases from a clean checkout of the main branch
- Test the release artifacts before announcing the release