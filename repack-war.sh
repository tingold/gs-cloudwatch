export MVN=../bin/apache-maven-3.2.2/bin/mvn
export GS_WAR=../boundless-bin/4.1.0/geoserver.war

#get rid of old stuff
rm geoserver.war

#build everything
$MVN clean install
$MVN dependency:copy-dependencies


#setup dirs
mkdir ./staging
unzip $GS_WAR -d ./staging
cp -f target/dependency/*.jar staging/WEB-INF/lib
cp -f target/*.jar staging/WEB-INF/lib
cd staging
zip -r geoserver.war *
mv geoserver.war ../
rm -rf staging
