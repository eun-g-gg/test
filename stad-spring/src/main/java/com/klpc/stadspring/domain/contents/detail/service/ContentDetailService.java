package com.klpc.stadspring.domain.contents.detail.service;

import com.klpc.stadspring.domain.contents.category.entity.ContentCategory;
import com.klpc.stadspring.domain.contents.category.repository.ContentCategoryRepository;
import com.klpc.stadspring.domain.contents.detail.controller.response.AddDetailResponse;
import com.klpc.stadspring.domain.contents.detail.entity.ContentDetail;
import com.klpc.stadspring.domain.contents.detail.repository.ContentDetailRepository;
import com.klpc.stadspring.domain.contents.detail.service.command.request.AddDetailRequestCommand;
import com.klpc.stadspring.global.response.ErrorCode;
import com.klpc.stadspring.global.response.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ContentDetailService {

    private final ContentDetailRepository repository;

    // 영상 스트리밍
    public ResponseEntity<ResourceRegion> streamingPublicVideo(HttpHeaders httpHeaders, Long id) {
        String pathStr = null;

        Optional<String> videoUrl = repository.findVideoUrlById(id);
        if (videoUrl.isPresent()) {
            pathStr = videoUrl.get();
        }
        // 파일 존재 확인
        try {
            UrlResource video = new UrlResource(pathStr);
            // - 파일 시스템 경로에 접근하기 위해서는 "file:"로 시작
            // - HTTP 프로토콜을 통해 자원에 접근하기 위해서는 "https:"로 시작
            // - FTP를 통해서 접근하기 위해서는 "ftp:"로 시작
            ResourceRegion resourceRegion;
            long chunkSize = 1024 * 1024;
            long contentLength = video.contentLength();

            Optional<HttpRange> optional = httpHeaders.getRange().stream().findFirst();
            HttpRange httpRange;
            if (optional.isPresent()) {
                log.info("ContentsService.streaming");
                httpRange = optional.get();
                long start = httpRange.getRangeStart(contentLength);
                long end = httpRange.getRangeEnd(contentLength);
                long rangeLength = Long.min(chunkSize, end - start + 1);
                resourceRegion = new ResourceRegion(video, start, rangeLength);
            } else {
                long rangeLength = Long.min(chunkSize, contentLength);
                //  chunk 사이즈로 자른 시작값부터 청크 사이즈 만큼 ResoureRegion을 return
                resourceRegion = new ResourceRegion(video, 0, rangeLength);
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES)) // 스트리밍을 위해 다운로드 받는 영상 캐시의 지속시간은 10분
                    .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    // header에 담긴 range 범위만큼 return
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes") // 데이터 타입은 bytes라고 헤더에 입력
                    .body(resourceRegion); // body에 가공한 데이터를 담아서 리턴
                    // 영상이 재생됨에 따라 해당 url로 다시 request를 요청하여 영상정보를 받아와서 재생
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    // id로 영상 상세 조회
    public ContentDetail getContentDetailById(Long id) {
        ContentDetail detail = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detail;
    }

    // conceptId로 id 조회
    public ContentDetail getContentDetailByConceptId(Long conceptId) {
        ContentDetail detail = repository.findContentDetailByConceptId(conceptId)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return detail;
    }

    // 인기 영상 목록
    // ========================== 태경 수정 ===================================
    public List<ContentDetail> getPopularContent() {
        List<ContentDetail> list = repository.findPopularContentDetail()
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return list;
    }

    // 최신 영상 목록
    // ========================== 태경 수정 ===================================
    public List<ContentDetail> getUpdatedContent() {
        List<ContentDetail> list = repository.findPopularContentDetail()
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITIY_NOT_FOUND));
        return list;
    }

    public AddDetailResponse addDetail(AddDetailRequestCommand command) {
        log.info("AddDetailRequestCommand : " + command);

        ContentDetail newContentDetail = ContentDetail.createContentDetail(
                command.getContentConceptId(),
                command.getEpisode(),
                command.getVideoUrl(),
                command.getSummary());
        repository.save(newContentDetail);

        return AddDetailResponse.builder().result("콘텐츠 디테일이 성공적으로 등록되었습니다.").build();
    }
}
