package com.halo.eventer.map.controller;



import com.halo.eventer.map.dto.map.StoreCreateDto;
import com.halo.eventer.map.dto.map.StoreCreateResDto;
import com.halo.eventer.map.service.MapService;
import com.halo.eventer.map.swagger.map.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지도")
@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;

    @MapCreateApi
    @PostMapping()
    public StoreCreateResDto createMap(@RequestBody StoreCreateDto storeCreateDto,
                                         @RequestParam("mapCategoryId") Long id){
        return mapService.createMap(storeCreateDto,id);
    }


    @GetStoreApi
    @GetMapping("/{mapId}")
    public ResponseEntity<?> getStore(@PathVariable("mapId")Long mapId){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapService.getMap(mapId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetStoresApi
    @GetMapping()
    public ResponseEntity<?> getStores(@RequestParam("mapCategoryId") Long mapCategoryId){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapService.getStores(mapCategoryId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @UpdateStoreApi
    @PatchMapping("/{mapId}")
    public ResponseEntity<?> updateStore(@PathVariable("mapId") Long id,
                                         @RequestBody StoreCreateDto storeCreateDto){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapService.updateStore(id, storeCreateDto));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @DeleteStoreApi
    @DeleteMapping("/{mapId}")
    public ResponseEntity<?> deleteStore(@PathVariable("mapId") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapService.deleteStore(id));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
