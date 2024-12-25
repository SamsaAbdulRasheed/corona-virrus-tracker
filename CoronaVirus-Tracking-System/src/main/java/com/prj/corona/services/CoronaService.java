package com.prj.corona.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.prj.corona.model.LocationState;

@Service
public class CoronaService {
	private List<LocationState>  allstate=new ArrayList<LocationState>();

	public List<LocationState> getAllstate() {
		return allstate;
	}

	public void setAllstate(List<LocationState> allstate) {
		this.allstate = allstate;
	} 
	
	private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
	
	@PostConstruct
//	<second> <minute> <hour> <day-of-month> <month> <day-of-week>
	@Scheduled(cron ="* * * 1 *  *")
	public void fetchData() throws IOException, InterruptedException {
		HttpClient client=HttpClient.newHttpClient();
		
		/*		HttpRequest.Builder builder= HttpRequest.newBuilder();
		URI uri= URI.create(VIRUS_DATA_URL);
		builder.uri(uri);
		HttpRequest request= builder.build(); */
		
		HttpRequest request=HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
		HttpResponse<String> response=client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader reader= new StringReader(response.body());
		
/*# CSVFormat.DEFAULT: A predefined format for handling CSV (Comma-Separated Values) data, provided by Apache Commons CSV.
  # withFirstRecordAsHeader(): Configures the parser to treat the first row of the CSV as the header (column names).
  # .parse(csvBodyreader): Parses the input data (csvBodyreader) into a collection of CSVRecord objects. */
		 
		Iterable<CSVRecord> record= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		List<LocationState> list=new ArrayList<LocationState>();
		for (CSVRecord records : record) {
			LocationState location=new LocationState();
			location.setState(records.get("Province/State"));
			location.setCountry(records.get("Country/Region"));
			int latestCase= Integer.parseInt(records.get(records.size()-1));
			int prevCase= Integer.parseInt(records.get(records.size()-2));
			location.setLastTotalDeath(latestCase);
			location.setDifferFromPrevay(prevCase);
			list.add(location);
			
		}
	/*	Using a temporary list like newstates is a better approach because:
			It resets the data each time the method runs.
			It ensures that only the latest data is stored in allstates.
			It prevents duplication and keeps the data size manageable. */
		
		this.allstate=list;
		
		
	}

}
