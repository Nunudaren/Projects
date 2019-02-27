package cn;

import org.junit.Test;

/**
 * 字典树（前缀树）的实现
 * 字典树又成为前缀树或者Trie树，是一种树形结构，有点是利用字符串的公共前缀来节约存储空间
 * 
 */
public class Trie {

	private TrieNode root;
	
	public Trie() {
		root = new TrieNode();
	}
	
	@Test
	public void test() {
		insert("abc");
		insert("abcd");
		insert("abd");
		insert("efg");
		insert("hik");
		System.out.println(search("abd"));
		delete("abd");
		System.out.println(search("abd"));
		System.out.println(prefixNumber("ab"));
		
	}
	
	/**
	 * 添加word,可以重复添加
	 * @param word
	 */
	public void insert(String word) {
		if (word == null) {
			return;
		}
		char[] chs = word.toCharArray();
		TrieNode node = root;
		int index = 0;
		for(int i = 0; i< chs.length; i++) {
			index = chs[i] - 'a';
			if(node.map[index] == null) {
				node.map[index] = new TrieNode();
			}
			node = node.map[index];
			node.path++;
		}
		node.end++;
	}

	/**
	 * 查询word是否在字典树中
	 * @param word
	 * @return
	 */
	public boolean search(String word) {
		if(word == null) {
			return false;
		}
		char[] chs = word.toCharArray();
		TrieNode node = root;
		int index = 0;
		for(int i = 0; i < chs.length; i++) {
			index = chs[i] - 'a';
			if(node.map[index] == null) {
				return false;
			}
			node = node.map[index];
		}
		return node.end != 0;
	}
	
	/**
	 * 删除word，如果word添加过多次，仅删除一次
	 * @param word
	 */
	public void delete(String word) {
		if(search(word)) {
			char[] chs = word.toCharArray();
			TrieNode node = root;
			int index = 0;
			for(int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if(node.map[index].path-- == 1) {//如果path值减完之后已经为0
					node.map[index] = null; //直接从当前节点的map中删除后续的所有路径，返回即可
					return;
				}
				node = node.map[index];
			}
			node.end--;
		}
	}
	
	/**
	 * 返回以字符串pre为前缀的单词数量
	 * @param pre
	 * @return
	 */
	public int prefixNumber(String pre) {
		if(pre == null) {
			return 0;
		}
		char[] chs = pre.toCharArray();
		TrieNode node = root;
		int index = 0;
		for(int i = 0; i < chs.length; i++) {
			index = chs[i] - 'a';
			if(node.map[index] == null) {
				return 0;
			}
			node = node.map[index];
		}
		return node.path;
	}
}

class TrieNode {
	public int path;//表示有多少个单词共用这个节点
	public int end;//表示又多少个单词以这个节点结尾
	public TrieNode[] map;//数组索引（key）代表节点的一条字符路径，值（value）代表字符路径指向的节点
	//在字符种类较多的情况下，可以选择使用真正的哈希表结构实现map
	
	public TrieNode() {
		path = 0;
		end = 0;
		map = new TrieNode[26];
	}
}