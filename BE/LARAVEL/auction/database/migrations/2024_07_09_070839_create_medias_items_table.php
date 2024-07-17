<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateMediasItemsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('mediasitems', function (Blueprint $table) {
            $table->string('MediaItemId', 10)->primary();
            $table->string('MediaId', 10);

            $table->text('Value');
            // Thiết lập khóa ngoại từ MediaId đến khóa chính MediaId của bảng media
            $table->foreign('MediaId')->references('MediaId')->on('medias');

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('medias_items');
    }
}
