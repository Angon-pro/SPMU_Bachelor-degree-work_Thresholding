# SPMU_Bachelor-degree-work_Thresholding
A thresholding algorithm realisation for thermogram analysis for the wildfires early detection system

Description:
A realisation of the thresholding algorithm for the fire detection purposes. The thermograms are obtained from a flight altitude. 
The program determines the temperatures in the input image according to defined temp scale and compares them to the specified anomaly temperature (threshold). 
After that, the program generates the resulting binary image based on threshold matrix and outputs the analysis report

Stack: Java, Maven

	/src/main/java/ path contains the source code
	/src/main/resources/ path contains directories with classified test images and resulting binary images

## Functioning
Flowchart (ru):
![image](https://github.com/Angon-pro/SPMU_Bachelor-degree-work_Thresholding/assets/85078037/ccf4f304-2d0d-4d48-9dd0-dd89714756a4)

Example:
![image](https://github.com/Angon-pro/SPMU_Bachelor-degree-work_Thresholding/assets/85078037/4b00966b-a50b-4832-acd9-45a4aad15e17)

## Efficiency
The accuracy of the solution is about ~90% (based on test thermograms). This value may vary depending on different inputs

Summary (ru):
![image](https://github.com/Angon-pro/SPMU_Bachelor-degree-work_Thresholding/assets/85078037/7f9bf9af-a49b-47d1-a582-a04b4e032410)
