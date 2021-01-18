package com.kyriexu;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2){
            AntPathMatcher matcher = new AntPathMatcher();
            boolean res = matcher.match(args[0], args[1]);
            System.out.print(res);
        }
    }
}
