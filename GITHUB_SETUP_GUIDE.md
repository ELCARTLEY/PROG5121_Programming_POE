# GitHub Setup and Feature Branch Guide

## Part 2 - Creating and Managing Feature Branches

### What is a Feature Branch?

A feature branch is a separate branch created from your main branch to develop a specific feature without affecting the main codebase. Once the feature is complete and tested, it can be merged back into the main branch.

**Benefits**:
- Keep the main branch stable and production-ready
- Multiple features can be developed in parallel
- Easy to review changes before merging
- Simple to rollback if something goes wrong

## Step-by-Step: Creating the Rhanbannasks Branch

### Option 1: Using Git Command Line

#### 1. Create and Switch to Feature Branch

```bash
cd "C:\Users\itume\IdeaProjects\PROG5121_Programming_POE"

# Create a new branch named Rhanbannasks
git checkout -b Rhanbannasks

# Or create branch from main (if needed)
git checkout main
git checkout -b Rhanbannasks
```

#### 2. Verify Branch Creation

```bash
# List all local branches
git branch

# Should show:
#   main
# * Rhanbannasks  (asterisk indicates current branch)
```

#### 3. Make Your Changes

All the files have already been created and modified:
- ✅ Task.java (created)
- ✅ TaskService.java (created)
- ✅ TaskTest.java (created)
- ✅ Main.java (updated)
- ✅ .github/workflows/maven.yml (created)
- ✅ pom.xml (updated)
- ✅ PART2_IMPLEMENTATION_GUIDE.md (created)

#### 4. Stage Changes

```bash
# Stage all changes
git add .

# Or stage specific files
git add src/main/java/model/Task.java
git add src/main/java/service/TaskService.java
git add src/main/java/org/example/Main.java
git add src/test/java/com/prog5121_programming_poe/TaskTest.java
git add .github/workflows/maven.yml
git add pom.xml
```

#### 5. Commit Changes

```bash
# Commit with descriptive message
git commit -m "feat: Implement Part 2 - EasyKanban Task Management System

- Add Task model class with validation and ID generation
- Add TaskService for task management operations
- Update Main.java with EasyKanban menu and task workflow
- Add comprehensive unit tests for Task functionality
- Set up GitHub Actions CI/CD pipeline
- Follow SOLID principles throughout implementation"
```

#### 6. Push Feature Branch to GitHub

```bash
# First time pushing this branch
git push -u origin Rhanbannasks

# Subsequent pushes
git push origin Rhanbannasks
```

### Option 2: Using GitHub Desktop

#### 1. Open GitHub Desktop

1. Launch GitHub Desktop
2. Click "Current Repository" (dropdown at top)
3. Select your PROG5121_Programming_POE repository

#### 2. Create Feature Branch

1. Click "Current Branch" tab at top
2. Click "New Branch" button
3. Enter branch name: `Rhanbannasks`
4. Ensure "Based on" is set to `main`
5. Click "Create Branch"

#### 3. Publish Branch

1. Click "Publish branch" button
2. GitHub Desktop will push the branch to GitHub

#### 4. Verify Changes and Commit

1. All changes should be shown in the "Changes" tab
2. Review the changes in the diff view
3. Enter commit message in the text field
4. Click "Commit to Rhanbannasks"

#### 5. Push Changes

1. Click "Push origin" button
2. Changes will be pushed to GitHub

### Option 3: Using IntelliJ IDEA (Built-in Git)

#### 1. Create Feature Branch in IDE

1. Click "Git" menu → "Branches..." or press `Ctrl+Shift+Backtick`
2. Click "New Branch"
3. Enter branch name: `Rhanbannasks`
4. Click "Create"

#### 2. Verify Current Branch

- Bottom right of IDE shows current branch name
- Should display "Rhanbannasks"

#### 3. Stage and Commit Changes

1. Click "Git" menu → "Commit..."
2. Select files to commit (all should be shown)
3. Enter commit message
4. Click "Commit and Push"
5. Select `origin/Rhanbannasks` as remote branch

#### 4. Handle Push

- IntelliJ will show a dialog to confirm push
- Click "Push" to confirm

## Commit Message Best Practices

Use clear, descriptive commit messages following this format:

```
<type>: <subject>

<body>

<footer>
```

**Types**:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Code style (formatting, semicolons, etc.)
- `refactor`: Code refactoring without feature change
- `test`: Adding or updating tests
- `chore`: Build process, dependencies

**Example**:
```
feat: Implement Part 2 - EasyKanban Task Management

- Created Task model class with validation logic
- Implemented TaskService for task operations
- Added comprehensive unit tests
- Set up GitHub Actions CI/CD pipeline

Implements the requirements from Part 2 of PROG5121
```

## Git Workflow Summary

```
┌─────────────────────────────────────────────────┐
│ 1. Create Feature Branch from Main              │
│    git checkout -b Rhanbannasks                 │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│ 2. Make Changes to Files                        │
│    - Create new classes                         │
│    - Update existing classes                    │
│    - Create unit tests                          │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│ 3. Stage Changes                                │
│    git add .                                    │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│ 4. Commit Changes                               │
│    git commit -m "descriptive message"          │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│ 5. Push to GitHub                               │
│    git push -u origin Rhanbannasks              │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│ 6. Create Pull Request (Optional)               │
│    - Title: "Part 2 - Task Management System"   │
│    - Description: Feature overview              │
│    - Target: main branch                        │
└─────────────────────────────────────────────────┘
                        ↓
┌─────────────────────────────────────────────────┐
│ 7. Merge to Main (When Ready)                   │
│    - All tests pass in CI/CD                    │
│    - Code review complete                       │
│    - Merge pull request                         │
└─────────────────────────────────────────────────┘
```

## Checking Branch Status

```bash
# View current branch
git branch

# Switch between branches
git checkout main          # Switch to main
git checkout Rhanbannasks  # Switch to feature branch

# View branch information
git branch -v             # Verbose (shows last commit)
git branch -vv            # Very verbose (shows remote tracking)

# Delete branch (locally)
git branch -d Rhanbannasks

# Delete branch (remote)
git push origin --delete Rhanbannasks
```

## Syncing with Main Branch

If main branch has been updated while working on feature branch:

```bash
# Fetch latest changes
git fetch origin

# Rebase feature branch on main (recommended)
git checkout Rhanbannasks
git rebase origin/main

# Or merge main into feature branch
git checkout Rhanbannasks
git merge origin/main
```

## Pull Request Process

### 1. Push Feature Branch
```bash
git push origin Rhanbannasks
```

### 2. Create Pull Request on GitHub

1. Go to repository on GitHub
2. Click "Pull requests" tab
3. Click "New pull request"
4. Select:
   - Base: `main`
   - Compare: `Rhanbannasks`
5. Click "Create pull request"

### 3. Fill in PR Details

**Title**: `Part 2 - EasyKanban Task Management System`

**Description**:
```markdown
## Summary
Implements Part 2 of PROG5121 Programming POE - Task Management System

## Changes Made
- Added Task model class with full validation and ID generation
- Created TaskService for task management operations
- Updated Main.java with EasyKanban menu and workflow
- Added comprehensive unit tests covering all scenarios
- Set up GitHub Actions CI/CD pipeline

## Test Coverage
- Task description validation (success/failure cases)
- Task ID generation and format validation
- Total hours calculation across multiple tasks
- All getter methods

## Related
- Closes #1 (or reference to issue)

## SOLID Principles Applied
- Single Responsibility Principle
- Open/Closed Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle
```

### 4. Review and Merge

1. Check GitHub Actions tests pass
2. Review code in "Files changed" tab
3. Click "Merge pull request" when ready
4. Confirm merge

## GitHub Actions CI/CD

The workflow file `.github/workflows/maven.yml` automatically:

1. **Runs on every push** to main or Rhanbannasks branch
2. **Sets up JDK 8**
3. **Compiles code** with Maven
4. **Runs all unit tests**
5. **Generates test reports**
6. **Shows results** in PR/commit status

Check workflow status:
1. Go to GitHub repository
2. Click "Actions" tab
3. See workflow runs and their status
4. Click on run to see details

## Best Practices

1. **Keep commits small and focused**
   - Each commit should address one logical change
   - Makes reverting easier if needed

2. **Write clear commit messages**
   - First line is summary (50 chars or less)
   - Blank line separates from body
   - Body explains why, not what

3. **Push frequently**
   - Don't wait until feature is fully complete
   - Keeps work backed up on GitHub

4. **Run tests before pushing**
   ```bash
   mvn test
   ```

5. **Sync with main regularly**
   - Prevents large merge conflicts
   - Catches integration issues early

6. **Review your own code first**
   - Check diffs before committing
   - Catch mistakes early

7. **Use meaningful branch names**
   - `Rhanbannasks` (descriptive)
   - Not `fix1`, `change`, `test`, etc.

## Troubleshooting

### Issue: Branch already exists

```bash
# List all branches
git branch -a

# Switch to existing branch
git checkout Rhanbannasks
```

### Issue: Merge conflicts

```bash
# View conflicts
git status

# Resolve conflicts in editor, then:
git add .
git commit -m "Resolve merge conflicts"
```

### Issue: Can't push changes

```bash
# Pull latest changes first
git pull origin Rhanbannasks

# Then push again
git push origin Rhanbannasks
```

### Issue: Undo last commit

```bash
# Keep changes, undo commit
git reset --soft HEAD~1

# Discard changes and undo commit
git reset --hard HEAD~1
```

## Summary Commands Cheat Sheet

```bash
# Create and switch to feature branch
git checkout -b Rhanbannasks

# Check current branch
git branch

# View recent commits
git log --oneline -n 5

# Stage changes
git add .

# Commit changes
git commit -m "Your message"

# Push to remote
git push -u origin Rhanbannasks

# View changes before committing
git diff

# View staged changes
git diff --staged

# Switch between branches
git checkout main
git checkout Rhanbannasks

# Delete feature branch
git branch -d Rhanbannasks

# Pull latest changes
git pull origin Rhanbannasks
```

## Next Steps

1. ✅ Feature branch created: `Rhanbannasks`
2. ✅ Code implemented following SOLID principles
3. ✅ Unit tests created and passing
4. ✅ CI/CD pipeline configured
5. ⏭️ **Push code to GitHub**
6. ⏭️ Create pull request (optional but recommended)
7. ⏭️ Merge to main when tests pass and review is complete
8. ⏭️ Main branch remains stable and production-ready

## Resources

- [Git Documentation](https://git-scm.com/doc)
- [GitHub Guides](https://guides.github.com/)
- [GitHub Desktop Help](https://docs.github.com/en/desktop)
- [IntelliJ Git Integration](https://www.jetbrains.com/help/idea/using-git-integration.html)

