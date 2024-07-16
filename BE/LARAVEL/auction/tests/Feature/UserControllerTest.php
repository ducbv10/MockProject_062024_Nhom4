<?php

namespace Tests\Feature;

use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class UserControllerTest extends TestCase
{
    use RefreshDatabase;

    protected $user;

    public function setUp(): void
    {
        parent::setUp();
        $this->user = User::factory()->create();
        $this->actingAs($this->user);
    }

    public function testUpdate()
    {
        $data = [
            'UserId' => 'US00000001',
            'UserName' => 'newusername',
            'PassWord' => 'newpassword',
            'Avatar' => 'new_avatar.jpg',
            'Name' => 'New Name',
            'DateOfBirth' => '1990-01-01',
            'Gender' => 'male',
            'Phone' => '1234567890',
            'Email' => 'newemail@example.com',
            'AddressId' => 'ADDR123',
            'IsBan' => 'unbanned',
            'Verify' => 'verified',
        ];

        $response = $this->putJson("/users/{$this->user->UserId}", $data);

        $response->assertStatus(200)
            ->assertJson($data);
    }

    public function testUpdateValidation()
    {
        $response = $this->putJson("/users/{$this->user->UserId}", [
            'Email' => 'not-an-email',
            'Gender' => 'invalid-gender',
            'IsBan' => 'invalid-status',
            'Verify' => 'invalid-status',
        ]);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['Email', 'Gender', 'IsBan', 'Verify']);
    }

    public function testUpdateNotFound()
    {
        $response = $this->putJson("/users/US00000001", ['Name' => 'Test']);
        $response->assertStatus(404);
    }
}
