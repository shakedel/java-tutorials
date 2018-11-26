package org.eladsh.tf_idf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.TextNode;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

import lombok.Value;
import lombok.val;

public class Tfidf {
	
private static final String PATTERN = Pattern.compile("[.,;!?(]$").pattern();
	private static final Path sites_dir_path = Paths.get("D:", "projects", "eclipse_workspace", "tf-idf", "src", "main", "resources", "sample_sites");
//	private static final Path sites_dir_path = Paths.get("D:", "projects", "eclipse_workspace", "tf-idf", "src", "main", "resources", "dev_sites");
	private static final int N = 10;

	public static void main( String[] args ) throws IOException {
    	Multiset<String> globalBow = HashMultiset.create();
		val baggedDocs = Files.list(sites_dir_path).map(t -> {
			try {
				return parseDoc(t, globalBow);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
		val idfs = idf(globalBow, Files.list(sites_dir_path).count());
		
		baggedDocs.stream().forEach(doc -> {
			List<String> terms = doc.topN(N, idfs);
			System.out.println(doc.docName+": "+Arrays.toString(terms.toArray()));
		});

//		idfs.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).forEach(e -> {
//			System.out.println(e.getValue()+" ("+globalBow.count(e.getKey())+") "+e.getKey());
//		});
    }
    
    
    @Value
    private static class BaggedDoc {
    	String docName;
    	Multiset<String> bow;
    	int numWords;
    	
    	private Double tf(Entry<String> r) {
//    		return ((double) r.getCount())/this.numWords;
    		return ((double) r.getCount());
    	}
    	
    	@Value
    	private static class TermScore implements Comparable<TermScore> {
    		String term;
    		double score;
			@Override
			public int compareTo(TermScore o) {
				return -1*Double.compare(score, o.getScore());
			}
    	}
    	
    	public List<String> topN(int n, Map<String, Double> idf) {
    		return bow.entrySet().stream().map(e -> new TermScore(e.getElement(), tf(e)*idf.get(e.getElement()))).sorted().limit(n).map(e->e.getTerm()).collect(Collectors.toList());
    	}
    	
    }
    
    private static BaggedDoc parseDoc(Path path, Multiset<String> globalBow) throws IOException {
    	Multiset<String> bow = HashMultiset.create();
    	val gzipStream = new GZIPInputStream(Files.newInputStream(path));
    	val doc = Jsoup.parse(gzipStream, "UTF-8", "http://sparkbeyond.com/");
    	val strings = doc.getAllElements().stream().flatMap(e -> e.textNodes().stream()).map(TextNode::text);;
    	val tokens = strings.map(s-> new StringTokenizer(s)).flatMap(Tfidf::getTokens);
    	val cleanTokens = tokens.map(Tfidf::cleanTokens);
    	cleanTokens.forEach(s -> {
    		globalBow.add(s);
    		bow.add(s);
    	});
    	return new BaggedDoc(path.getFileName().toString().substring(7), bow, sumCount(bow));
    }
    
    private static Stream<String> getTokens(StringTokenizer st) {
    	val tokens = new LinkedList<String>();
		while (st.hasMoreTokens()) {
			tokens.add(st.nextToken());
		}
		return tokens.stream();
    }
    
    private static String cleanTokens(String s) {
    	return s.replaceAll(PATTERN, "").toLowerCase();
    }

    public static <R> int sumCount(Multiset<R> ms) {
    	int total = 0;
    	for (Entry<R> r: ms.entrySet()) {
    		total =+ r.getCount();
    	}
    	return total;
    }
    
    private static Map<String, Double> idf(Multiset<String> bow, long n) {
//    	int n = sumCount(bow);
    	val idfs = new TreeMap<String, Double>();
    	for (Entry<String> r: bow.entrySet()) {
    		val idf = Math.log(((double) n)/r.getCount());
    		idfs.put(r.getElement(), idf);
    	}
    	return idfs;
    }
    
    
}
