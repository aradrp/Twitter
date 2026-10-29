$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$serverRoot = Join-Path $projectRoot 'Server'
$buildRoot = Join-Path $serverRoot 'build'
New-Item -ItemType Directory -Force -Path $buildRoot | Out-Null
$sources = Get-ChildItem (Join-Path $serverRoot 'src') -Filter '*.java' | ForEach-Object FullName
& javac -d $buildRoot $sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Push-Location $serverRoot
try { & java -cp $buildRoot Main } finally { Pop-Location }
