$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$clientRoot = Join-Path $projectRoot 'Client'
$buildRoot = Join-Path $clientRoot 'build'
New-Item -ItemType Directory -Force -Path $buildRoot | Out-Null
$sources = Get-ChildItem (Join-Path $clientRoot 'src') -Filter '*.java' | ForEach-Object FullName
& javac -d $buildRoot $sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Push-Location $clientRoot
try { & java -cp $buildRoot Main } finally { Pop-Location }
