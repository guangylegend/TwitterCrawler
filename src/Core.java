import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class Core {
	public static void main(String[] args) throws IOException, TwitterException{
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("ConsumerKey_Here")
		  .setOAuthConsumerSecret("ConsumerSecret_Here")
		  .setOAuthAccessToken("AccessToken_Here")
		  .setOAuthAccessTokenSecret("AccessTokenSecret_Here");
		
		String outfile = "output.txt";
		if(args.length > 0)outfile = args[0];
		PrintStream ps = new PrintStream(new FileOutputStream(outfile));   
		
		//set listener
		StatusListener listener = new StatusListener(){
	        public void onStatus(Status status) {
	            ps.println(status.getUser().getName() + " : " + status.getText());
	        }
	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
			public void onScrubGeo(long arg0, long arg1) {}
			public void onStallWarning(StallWarning stallWarning) {}
	    };
	    TwitterStreamFactory tsf = new TwitterStreamFactory(cb.build());
	    TwitterStream twitterStream = tsf.getInstance();
	    twitterStream.addListener(listener);
	    
	    //set filter
	    FilterQuery filter = new FilterQuery();
	    filter.language("en");
	    filter.track(new String[]{"Donald Trump"});
	    twitterStream.filter(filter);   
	    
	    //twitterStream.sample();
	    
	}
}
