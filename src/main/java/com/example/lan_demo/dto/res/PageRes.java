package com.example.lan_demo.dto.res;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRes {

    private Long pageNo;//trang hiện tại

    private Long pageSize;// số bản ghi trên 1 trang

    private Long totalPage;//tổng số trang

    private Long totalRecord;//tổng số bản ghi

    private List<UserRes> record;//danh sách bản ghi
}
