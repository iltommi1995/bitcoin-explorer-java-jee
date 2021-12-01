package com.z9devs.bitcoinexplorerjee.models;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Block 
{
	private String hash;
	private int height;
	private long version;
	private String merkleroot;
	private long time;
	private long nonce;
	private long difficulty;
	private String previousblockhash;
	private int size;
	private int weight;
	private int nTx;
	private ArrayList<Transaction> tx;
	
	public Block() {}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getMerkleroot() {
		return merkleroot;
	}

	public void setMerkleroot(String merkleroot) {
		this.merkleroot = merkleroot;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getNonce() {
		return nonce;
	}

	public void setNonce(long nonce) {
		this.nonce = nonce;
	}

	public long getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(long difficulty) {
		this.difficulty = difficulty;
	}

	public String getPreviousblockhash() {
		return previousblockhash;
	}

	public void setPreviousblockhash(String previousblockhash) {
		this.previousblockhash = previousblockhash;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getnTx() {
		return nTx;
	}

	public void setnTx(int nTx) {
		this.nTx = nTx;
	}

	public ArrayList<Transaction> getTx() {
		return tx;
	}

	public void setTx(ArrayList<Transaction> tx) {
		this.tx = tx;
	};
	

}
