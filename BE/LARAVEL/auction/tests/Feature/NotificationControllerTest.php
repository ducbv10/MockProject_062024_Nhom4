<?php

namespace Tests\Feature;

use App\Models\Notification;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class NotificationControllerTest extends TestCase
{
    use RefreshDatabase;

    protected $user;

    public function setUp(): void
    {
        parent::setUp();
        $this->user = User::factory()->create();
        $this->actingAs($this->user);
    }

    public function testIndex()
    {
        Notification::factory()->count(3)->create();
        $response = $this->getJson('/notifications');
        $response->assertStatus(200)
            ->assertJsonCount(3);
    }

    public function testStore()
    {
        $data = [
            'UserId' => $this->user->UserId,
            'Content' => 'New message received.',
            'Title' => 'New Message',
        ];
        $response = $this->postJson('/notifications', $data);
        $response->assertStatus(201)
            ->assertJsonStructure(['UserId', 'Content', 'Title'])
            ->assertJson($data);
    }

    public function testShow()
    {
        $notification = Notification::factory()->create();
        $response = $this->getJson("/notifications/{$notification->UserId}");
        $response->assertStatus(200)
            ->assertJson($notification->toArray());
    }

    public function testUpdate()
    {
        $notification = Notification::factory()->create();
        $data = [
            'UserId' => $this->user->UserId,
            'Content' => 'Updated message content.',
            'Title' => 'Updated Message',
        ];
        $response = $this->putJson("/notifications/{$notification->UserId}", $data);
        $response->assertStatus(200)
            ->assertJson($data);
    }

    public function testDestroy()
    {
        $notification = Notification::factory()->create();
        $response = $this->deleteJson("/notifications/{$notification->UserId}");
        $response->assertStatus(200)
            ->assertJson(['message' => 'Notification deleted successfully']);
        $this->assertDatabaseMissing('notifications', ['id' => $notification->UserId]);
    }

    public function testStoreValidation()
    {
        $response = $this->postJson('/notifications', []);
        $response->assertStatus(422)
            ->assertJsonValidationErrors(['UserId', 'Content', 'Title']);
    }

    public function testShowNotFound()
    {
        $response = $this->getJson("/notifications/NT00000001");
        $response->assertStatus(404);
    }
}
