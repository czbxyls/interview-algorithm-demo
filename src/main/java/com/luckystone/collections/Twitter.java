package com.luckystone.collections;
import java.util.*;
import java.util.LinkedList;

public class Twitter {

    private class Tweet {
        int tweetId;
        int time;

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    private Map<Integer, Set<Integer>> followees = new HashMap<>(); //我关注的账号
    private Map<Integer, LinkedList<Tweet>> myTweets = new HashMap<>(); //我发的推

    private final static int MAX = 10;
    private int seq = 0;

    /** Initialize your data structure here. */
    public Twitter() {
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        myTweets.putIfAbsent(userId, new LinkedList<>());
        LinkedList<Tweet> myTweetList = myTweets.get(userId);
        if(myTweetList.size() == MAX) {
            myTweetList.removeLast();
        }
        Tweet tweet = new Tweet(tweetId, ++seq);
        myTweetList.addFirst(tweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user followed or by the user herself.
     * Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> tweetQueue = new PriorityQueue<>(new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                if(o1.time == o2.time) return 0;
                return o1.time < o2.time ? -1 : 1;
            }
        });

        LinkedList<Tweet> tweets = myTweets.get(userId);
        if(tweets != null) {
            for(Tweet tweet : tweets) {
                tweetQueue.add(tweet);
                if(tweetQueue.size() > MAX) {
                    Tweet tweetId = tweetQueue.poll();
                }
            }
        }
        Set<Integer> followers = followees.get(userId);
        if(followers != null) {
            for(Integer uid : followers) {
                tweets = myTweets.get(uid);
                if(tweets != null) {
                    for(Tweet tweet : tweets) {
                        tweetQueue.add(tweet);
                        if(tweetQueue.size() > MAX) tweetQueue.poll();
                    }
                }
            }
        }

        List<Tweet> list = new ArrayList<>(tweetQueue.size());
        for(Tweet tweet : tweetQueue) {
            list.add(tweet);
        }
        list.sort(new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                if(o1.time == o2.time) return 0;
                return o1.time > o2.time ? -1 : 1;
            }
        });
        List<Integer> result = new ArrayList<>();
        for(Tweet tweet : list) result.add(tweet.tweetId);
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId == followeeId) return;
        followees.putIfAbsent(followerId, new HashSet<>());
        followees.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId) return;
        Set<Integer> set = followees.get(followerId);
        if(set != null) set.remove(followeeId);
    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(2, 5);

       // System.out.println(Arrays.toString(twitter.getNewsFeed(1).toArray()));

        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        System.out.println(Arrays.toString(twitter.getNewsFeed(1).toArray()));

        twitter.unfollow(1, 2);
        System.out.println(Arrays.toString(twitter.getNewsFeed(1).toArray()));

    }
}
