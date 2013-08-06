#! /bin/bash

# A bash script to update GTFS data and
# create position vectors from a a real-
# time SIRI feed.

# These vectors are passed as JSON to
# "json2csv.py", where they are added
# to csv files for use by the Hadoop
# cluster.

# This program currenly assumes eastern
# time, subject to daylight savings.

# 	Written by Otho Ulrich for 
# Washtenaw Community College
# and its associates. (c) 2013



# Prerequisites
#
# unzip, wget, python


# Program settings.
client_url="bosapp.wccnet.edu"

old_pwd=`pwd`

source_dir="/home/o/u/oulrich/projects/big_data/src"
data_dir="${source_dir}/data"
tmp_dir="${source_dir}/tmp"

dump_file="${tmp_dir}/siridump.json"
vector_file="${tmp_dir}/vectors.json"
route_file="${tmp_dir}/routes.json"

python="python2.7"
new_vector_program="${python} ${source_dir}/new_vector.py"

# SIRI Source Settings
########################
# Settings for MTA, Manhattan
# MTA dev key: 29259c91-ecbc-4555-b425-bcf4b7a71a4c
target_name="MTA"
api_key="29259c91-ecbc-4555-b425-bcf4b7a71a4c"

gtfs_file="google_transit_manhattan.zip"
gtfs_file_loc="http://mta.info/developers/data/nyct/bus"

target_json="bustime.mta.info/api/siri/vehicle-monitoring.json"
monitoring_ref="308214"
route_id="M66"
get_opts="key=${api_key}"

traj_dir="${data_dir}/${target_name}"


# Begin Tasks
##############
echo [gather_vectors.sh]
echo [gather_vectors.sh] New Run: `date`
echo [gather_vectors.sh] This script will gather vectors from ${target_name}.


# Prepare Workspace
####################
echo [gather_vectors.sh] Preparing workspace.
mkdir -p ${traj_dir}
mkdir -p ${tmp_dir}
cd ${tmp_dir}



# Retrieve GTFS Data
#####################
# Uncomment to enable GTFS retrieval.
#
# Todo: 
# Compare creation dates for downloaded
# gtfs files to existing, if they exist.
# Replace only if downloaded files are newer.
# Since I'm already downloading them, this
# doesn't actually save me anything.
#
# How can the date be checked remotely?
#

# echo [gather_vectors.sh] Retrieving GTFS file.
# cd ${tmp_dir}
# wget -q ${gtfs_file_loc}/${gtfs_file}
# mkdir -p ${data_dir}/gtfs
# unzip -o ${gtfs_file} -d ${data_dir}/gtfs


# JSON Request using Curl
#################################
echo [gather_vectors.sh] Downloading SIRI API output.
#echo [gather_vectors.sh] Using curl to retrieve "http://${target_json}."

curl -s ${target_json}\?${get_opts} > ${dump_file}


# Filter JSON results with jq.
###############################
echo [gather_vectors.sh] Parsing JSON.
#echo [gather_vectors.sh] Using jq to parse JSON.

# Reduce the jq command
jq="${source_dir}/jq"

# Find Active Routes
#####################
echo [gather_vectors.sh] Filtering for active routes.
$jq '[{"route_id": .Siri.ServiceDelivery.VehicleMonitoringDelivery[].VehicleActivity[].MonitoredVehicleJourney.PublishedLineName}]' ${dump_file} > ${route_file}

# Build Vectors
################
echo [gather_vectors.sh] Building vectors.
$jq '.Siri.ServiceDelivery.VehicleMonitoringDelivery[].VehicleActivity[]' ${dump_file} | $jq -c '{"date": .RecordedAtTime, MonitoredVehicleJourney}'  | $jq -c '{"t":.date, "y": .MonitoredVehicleJourney.VehicleLocation.Latitude, "x": .MonitoredVehicleJourney.VehicleLocation.Longitude, "route_id": .MonitoredVehicleJourney.PublishedLineName }' > ${vector_file}

# For loop translates each vector and appends it to
# its route's trajectory database.
echo [gather_vectors.sh] Editing vectors and writing to databases.

for vector in `cat $vector_file`
do
	datetime=`echo ${vector}|$jq -c .t|sed 's/\([0123456789][0123456789][0123456789][0123456789]-[0123456789][0123456789]-[0123456789][0123456789]\)T\([0123456789][0123456789]:[0123456789][0123456789]:[0123456789][0123456789].[0123456789][0123456789][0123456789]-[0123456789][0123456789]:[0123456789][0123456789]\)/\1 \2/'|sed 's/"//g'`
	posixtime=`date -d "$datetime" +%s`
	route_id=`echo ${vector}|$jq -c '.route_id'|sed 's/"//g'`

	route_database="${traj_dir}/${route_id}.traj"
	mkdir -vp ${data_dir}/${target_name}

	echo "${posixtime},`echo ${vector}|$jq '.x'`,`echo ${vector}|$jq '.y'`" >> ${route_database}
done


# Finished JSON processing.
echo [gather_vectors.sh] Done processing JSON.


# Clean-up
###########
echo [gather_vectors.sh] Cleaning up.
# echo [gather_vectors.sh] Removing temporary files.

rm -rf ${tmp_dir}	

echo [gather_vectors.sh] Data pull complete.

cd ${old_pwd}
exit 0
