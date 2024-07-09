<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            // UserId làm khóa chính, varchar(10)
            $table->string('UserId', 10)->primary();

            // UserName, PassWord, Avatar, Name, Email là varchar(MAX)
            $table->string('UserName');
            $table->string('PassWord');
            $table->string('Avatar')->nullable();
            $table->string('Name');

            // DateOfBirth là date
            $table->date('DateOfBirth')->nullable();

            // Gender là enum ['male', 'female']
            $table->enum('Gender', ['male', 'female'])->nullable();

            // Phone là varchar(MAX)
            $table->string('Phone')->nullable();

            $table->string('Email');

            // AddressId làm khóa ngoại, varchar(10)
            $table->string('AddressId', 10)->nullable();

            // IsBan là enum ['banned', 'unbanned'], Verify là enum ['verified', 'unverified']
            $table->enum('IsBan', ['banned', 'unbanned'])->default('unbanned');
            $table->enum('Verify', ['verified', 'unverified'])->default('unverified');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
