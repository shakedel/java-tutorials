package org.eladsh.dispatch_server.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eladsh.dispatch_server.job.Job;
import org.eladsh.dispatch_server.job.JobRequirements;
import org.eladsh.dispatch_server.job.Predicates;
import org.eladsh.dispatch_server.server.ServerFeature.Architecture;
import org.eladsh.dispatch_server.server.ServerFeature.GPU;
import org.eladsh.dispatch_server.server.ServerFeature.Memory;
import org.eladsh.dispatch_server.server.ServerFeature.OS;
import org.eladsh.dispatch_server.utils.WeightedRand;

import com.github.javafaker.Faker;

import lombok.val;

public class Generators {

	public static final Architecture[] architectures = ServerFeature.Architecture.values();
	public static final GPU[] gpus = ServerFeature.GPU.values();
	public static final Memory[] mems = ServerFeature.Memory.values();
	public static final OS[] oss = ServerFeature.OS.values();
	
	public static int DEFAULT_NUM_SERVERS=20;
	public static int DEFAULT_NUM_JOBS=200;
	
	public static long SEED = 6134573826648309141L;
	
	private final Random random;
	
	public Generators(Random rand) {
		this.random = rand;
	}
	public Generators(long seed) {
		this(new Random(seed));
	}
	public Generators() {
		this(new Random());
	}
	
	public static void main(String[] args) {
		val gen = new Generators(SEED);
		
		val servers = IntStream.range(0, DEFAULT_NUM_SERVERS).mapToObj(__ -> gen.genServer()).collect(Collectors.toList());
		val jobs = IntStream.range(0, DEFAULT_NUM_JOBS).mapToObj(id -> gen.genJob(id)).collect(Collectors.toList());
		
		System.out.println("Servers:");
		System.out.println("=============================");
		String[] columns = {"id","arch","gpu","mem","os"};
		System.out.println(String.join(",", columns));
		servers.stream().map(Server::toCsvString).forEach(System.out::println);
		System.out.println("Jobs:");
		System.out.println("=============================");
		jobs.stream().forEach(System.out::println);
		System.out.println("Jobs Fullfilement:");
		System.out.println("=============================");
		jobs.stream().forEach(job -> {
			val validServers = servers.stream().filter(server -> job.getRequirements().test(server.config)).collect(Collectors.toList());
			System.out.print(validServers.size()+", ");
		});
	}

	
	public Server genServer() {
		val faker = new Faker(random);
		val id = faker.ancient().hero();
		val cfg = genServerConf();
		return new Server(id, cfg);
	}
	
	public Job genJob(int id) {
		val reqs = genJobRequirements();
		val duration = genDuration();
		return new Job(id, duration, reqs);
	}

	private int genDuration() {
		val duration = IntStream.range(1, 10).boxed().toArray();
		val weights = Arrays.stream(duration).mapToInt(d -> (int) Math.ceil(Math.sqrt((int) d))).toArray();
		return (int) WeightedRand.weightedRand(random, weights, duration);
	}

	private ServerConfig genServerConf() {
		val arch = architectures[random.nextInt(architectures.length)];
		val gpu = gpus[random.nextInt(gpus.length)];
		val mem = mems[random.nextInt(mems.length)];
		val os = oss[random.nextInt(oss.length)];
		return new ServerConfig(arch, mem, os, gpu);
	}
	
	private int genNumPreds() {
		return WeightedRand.weightedRand(random, new int[]{1, 1, 2, 3, 3}, new Integer[]{0, 1, 2, 3, 4});
	}
	private List<OS> genOss() {
		val num_os = random.nextInt(Generators.oss.length-1)+1;
		val permOs = new ArrayList<OS>(Arrays.asList(Generators.oss));
		Collections.shuffle(permOs, random);
		return permOs.subList(0, num_os);
	}
	
	private JobRequirements genJobRequirements() {
		val server = this.genServer();
		val oss = genOss();
		val allPreds = new ArrayList<Predicate<ServerConfig>>();
		allPreds.add(Predicates.architectureIs(server.config.getArchitecture()));
		allPreds.add(Predicates.osIn(oss));
		allPreds.add(Predicates.minimumMemory(server.config.getMemory().memGb));
		allPreds.add(Predicates.minimumGpu(server.config.getGpu()));
		Collections.shuffle(allPreds, random);
		val preds = allPreds.subList(0, genNumPreds());
		return new JobRequirements(preds);
	}


}
