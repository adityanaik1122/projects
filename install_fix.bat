@echo off
echo Uninstalling current TensorFlow...
pip uninstall -y tensorflow tensorflow-intel keras

echo Installing TensorFlow CPU version...
pip install tensorflow-cpu==2.15.0

echo Installation complete!
pause
