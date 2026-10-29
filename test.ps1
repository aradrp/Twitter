$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$clientRoot = Join-Path $projectRoot 'Client'
$buildRoot = Join-Path $clientRoot 'build'
New-Item -ItemType Directory -Force -Path $buildRoot | Out-Null
$sources = Get-ChildItem (Join-Path $clientRoot 'src') -Filter '*.java' | ForEach-Object FullName
$sources += Join-Path $projectRoot 'tests/IntegrationSmoke.java'
& javac -d $buildRoot $sources
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
& java -cp $buildRoot IntegrationSmoke
exit $LASTEXITCODE
