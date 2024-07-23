<?php

namespace App\Http\Controllers;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Http\Request;
use App\Models\Asset;

class AuctionController extends Controller
{
    public function auctionedProducts()
    {
        $assets = Asset::where('IsInAuction', 'inauctioned')
                        ->with(['media.mediaItems', 'auctionDetail.auction'])
                        ->get()
                        ->map(function ($asset) {
                            return [
                                'AssetId' => $asset->AssetId,
                                'Name' => $asset->Name,
                                'StartDay' => $asset->auctionDetail->auction->StartDate ?? null,
                                'Image' => $asset->media->map(function ($media) {
                                    return $media->mediaItems->map(function ($mediaItem) {
                                        return $mediaItem->Value;
                                    });
                                })->flatten(),
                                'StartingPrice' => $asset->auctionDetail->StartingPrice ?? null
                            ];
                        });

        return response()->json(['data' => $assets]);
    }

    public function successfullyAuctionedProducts()
    {
        $assets = Asset::whereHas('auctionDetail.auction', function($query) {
                                $query->where('Status', 'finished');
                            })
                            ->with(['media.mediaItems', 'auctionDetail.auction'])
                            ->get()
                            ->map(function ($asset) {
                                return [
                                    'AssetId' => $asset->AssetId,
                                    'Name' => $asset->Name,
                                    'EndDate' => $asset->auctionDetail->auction->EndDate ?? null,
                                    'FinalPrice' => $asset->auctionDetail->PresentPrice ?? null,
                                    'Image' => $asset->media->map(function ($media) {
                                        return $media->mediaItems->map(function ($mediaItem) {
                                            return $mediaItem->Value;
                                        });
                                    })->flatten()
                                ];
                            });

        return response()->json(['data' => $assets]);
    }
}
