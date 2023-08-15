package com.kevin.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-07-30 21:09
 */
@Data
public class ChapterVO {
    private String id;
    private String title;
    // 一个章节中有多个小节
    private List<VideoVO> children=new ArrayList<>();
}
